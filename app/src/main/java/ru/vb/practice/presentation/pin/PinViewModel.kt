package ru.vb.practice.presentation.pin

import android.content.Context
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.vb.practice.R
import ru.vb.practice.domain.GetBiometricConsentUseCase
import ru.vb.practice.domain.GetPinUseCase
import ru.vb.practice.domain.SetBiometricConsentUseCase
import ru.vb.practice.domain.SetPinUseCase
import ru.vb.practice.domain.ValidatePinUseCase


class PinViewModel(
    private val getPinUseCase: GetPinUseCase,
    private val setPinUseCase: SetPinUseCase,
    private val validatePinUseCase: ValidatePinUseCase,
    private val setBiometricConsentUseCase: SetBiometricConsentUseCase,
    private val getBiometricConsentUseCase: GetBiometricConsentUseCase,
) : ViewModel() {

    private val _viewState = MutableStateFlow(PinViewState())
    val viewState: StateFlow<PinViewState> = _viewState.asStateFlow()

    init {
        initialize()
    }

    private fun initialize() {
        viewModelScope.launch {
            val pin = getPinUseCase()
            val isPinSet = isPinSet()

            _viewState.value = PinViewState(
                state = if (pin.isNullOrEmpty()) PinState.SettingPin else PinState.Initial,
                isPinSet = isPinSet,
                isBiometricConsent = getBiometricConsentUseCase(),
                isInitialized = true
            )
        }
    }

    fun handleIntent(intent: PinIntent) {
        viewModelScope.launch {
            if (viewState.value.isInputBlocked) return@launch
            when (intent) {
                is PinIntent.ValidatePin -> validatePin(intent.pin)
                is PinIntent.SetPin -> setPin(intent.pin)
                is PinIntent.RepeatPin -> repeatPin(intent.pin)
                is PinIntent.AttemptBiometricAuthentication -> authenticateBiometrics(intent.isSuccess)
                is PinIntent.Reset -> resetState()
                is PinIntent.ChangePinCode -> handlePinChange(intent.pin)
                is PinIntent.SetBiometricConsent -> {
                    _viewState.value = viewState.value.copy(isBiometricConsent = intent.consent)
                }
            }
        }
    }

    private fun handlePinChange(pin: String) {
        if (viewState.value.isInputBlocked) return // Игнорировать ввод, если ввод заблокирован

        when (viewState.value.state) {
            is PinState.SettingPin -> handleIntent(PinIntent.SetPin(pin))
            is PinState.ConfirmingPin -> handleIntent(PinIntent.RepeatPin(pin))
            else -> handleIntent(PinIntent.ValidatePin(pin))
        }
    }

    fun showBottomSheetOrUseBiometric(context: FragmentActivity) {
        viewModelScope.launch {
            val biometricManager = BiometricManager.from(context)
            _viewState.value = viewState.value.copy(canUseBiometric = biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS)
            if (viewState.value.isInitialized) {
                if (viewState.value.isPinSet && viewState.value.canUseBiometric) {
                    when (viewState.value.isBiometricConsent) {
                        true -> authenticateBiometric(context)
                        false -> return@launch
                        else -> _viewState.value = viewState.value.copy(isShowBottomSheet = true)
                    }
                }
            }
        }
    }

    fun authenticateBiometric(activity: FragmentActivity) {
        viewModelScope.launch {
            authenticate(activity) { isSuccess ->
                handleIntent(PinIntent.AttemptBiometricAuthentication(isSuccess))
            }
        }
    }

    // Функция для выполнения биометрической аутентификации
    private fun authenticate(context: Context, callback: (Boolean) -> Unit) {
        val executor = ContextCompat.getMainExecutor(context)
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(context.getString(R.string.biometric_auth_title))
            .setNegativeButtonText(context.getString(R.string.cancel))
            .build()

        val biometricPrompt = BiometricPrompt(
            context as FragmentActivity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    callback(false)
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    callback(true)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    callback(false)
                }
            }
        )
        biometricPrompt.authenticate(promptInfo)
    }

    fun changeShowBottomSheet(value: Boolean) {
        _viewState.value = viewState.value.copy(isShowBottomSheet = value)
    }

    fun setBiometricConsent(consent: Boolean) {
        viewModelScope.launch {
            setBiometricConsentUseCase(consent)
            _viewState.value = viewState.value.copy(isBiometricConsent = consent)
        }
    }

    private fun authenticateBiometrics(success: Boolean) {
        if (success) _viewState.value = viewState.value.copy(state = PinState.AuthenticationSuccess)
    }

    private suspend fun resetState() {
        val pin = getPinUseCase()
        _viewState.value =
            viewState.value.copy(state = if (pin.isNullOrEmpty()) PinState.SettingPin else PinState.Initial)
    }

    private suspend fun repeatPin(pin: String) {
        if (viewState.value.tempPin == pin) {
            setPinUseCase(pin)
            _viewState.value = viewState.value.copy(tempPin = null, state = PinState.PinSetSuccess)
        } else {
            _viewState.value = viewState.value.copy(state = PinState.PinMismatch)
        }
    }

    private fun setPin(pin: String) {
        _viewState.value = viewState.value.copy(tempPin = pin, state = PinState.ConfirmingPin)
    }

    private suspend fun validatePin(pin: String) {
        if (validatePinUseCase(pin)) {
            _viewState.value = viewState.value.copy(state = PinState.AuthenticationSuccess)
        } else {
            _viewState.value = viewState.value.copy(state = PinState.AuthenticationFailed)
        }
    }

    private suspend fun setTimer2s() {
        delay(2000)
    }

    private fun updatePinCode(newPinCode: String) {
        _viewState.value = viewState.value.copy(pinCode = newPinCode)
    }

    fun addDigit(digit: String) {
        if (viewState.value.pinCode.length < 4) {
            _viewState.value = viewState.value.copy(pinCode = viewState.value.pinCode + digit)
        }
    }

    fun deleteLastDigit() {
        if (viewState.value.pinCode.isNotEmpty()) {
            _viewState.value = viewState.value.copy(
                pinCode = viewState.value.pinCode.substring(
                    0,
                    viewState.value.pinCode.length - 1
                )
            )
        }
    }

    private suspend fun isPinSet(): Boolean {
        return getPinUseCase() != null
    }

    private fun setInputBlocked(isBlocked: Boolean) {
        _viewState.value = viewState.value.copy(isInputBlocked = isBlocked)
    }

    fun handlePinState() {
        viewModelScope.launch {
            _viewState.value = viewState.value.copy(pinCode = "")
            when (viewState.value.state) {
                is PinState.AuthenticationSuccess, is PinState.PinSetSuccess -> {
                    _viewState.value = viewState.value.copy(isInputBlocked = true)
                    setTimer2s()
                    // Возвращаем результат для навигации
                    _viewState.value = viewState.value.copy(state = PinState.NavigateToHome)
                }

                is PinState.AuthenticationFailed, is PinState.PinMismatch -> {
                    _viewState.value = viewState.value.copy(isInputBlocked = true)
                    setTimer2s()
                    _viewState.value = viewState.value.copy(isInputBlocked = false)
                    handleIntent(PinIntent.Reset)
                }

                else -> {}
            }
        }
    }
}