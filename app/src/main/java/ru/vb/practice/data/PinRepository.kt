package ru.vb.practice.data

interface PinRepository {
    suspend fun savePin(pin: String)
    suspend fun getPin(): String?
    suspend fun validatePin(pin: String): Boolean
    suspend fun setBiometricConsent(consent: Boolean)
    suspend fun getBiometricConsent(): Boolean?
}