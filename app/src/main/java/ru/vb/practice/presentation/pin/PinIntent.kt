package ru.vb.practice.presentation.pin

sealed class PinIntent {
    data class ValidatePin(val pin: String): PinIntent()
    data class SetPin(val pin: String): PinIntent()
    data class RepeatPin(val pin: String): PinIntent()
    data class AttemptBiometricAuthentication(val isSuccess: Boolean) : PinIntent()
    object Reset : PinIntent()
    data class ChangePinCode(val pin: String): PinIntent()
    data class SetBiometricConsent(val consent: Boolean): PinIntent()
}