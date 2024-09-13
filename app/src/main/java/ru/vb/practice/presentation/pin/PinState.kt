package ru.vb.practice.presentation.pin

sealed class PinState {
    object Initial : PinState() // Начальное состояние
    object SettingPin : PinState() // Установка нового PIN-кода
    object ConfirmingPin : PinState() // Подтверждение PIN-кода
    object AuthenticationSuccess : PinState() // PIN-код прошел проверку
    object AuthenticationFailed : PinState() // PIN-код не прошел проверку
    object PinSetSuccess : PinState() // PIN-код успешно установлен
    object PinMismatch : PinState() // PIN-коды не совпадают
    object NavigateToHome : PinState() // Переход на домашний экран
}