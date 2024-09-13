package ru.vb.practice.presentation.pin

data class PinViewState(
    val state: PinState = PinState.Initial,
    val pinCode: String = "",
    val tempPin: String? = null,
    val isPinSet: Boolean = false,
    val isBiometricConsent: Boolean? = null,
    val isInputBlocked: Boolean = false,
    val isShowBottomSheet: Boolean = false,
    val canUseBiometric: Boolean = false,
    val isInitialized: Boolean = false
)