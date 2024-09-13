package ru.vb.practice.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class PinRepositoryImpl(private val dataStoreManager: DataStoreManager) : PinRepository {

    override suspend fun savePin(pin: String) {
        withContext(Dispatchers.IO) {
            dataStoreManager.savePin(pin)
        }
    }

    override suspend fun getPin(): String? {
        return withContext(Dispatchers.IO) {
            dataStoreManager.getPin().firstOrNull()
        }
    }

    override suspend fun validatePin(pin: String): Boolean {
        val hashedPin = dataStoreManager.hashPin(pin)
        return withContext(Dispatchers.IO) {
            dataStoreManager.getPin().firstOrNull() == hashedPin
        }
    }

    override suspend fun setBiometricConsent(consent: Boolean) {
        withContext(Dispatchers.IO) {
            dataStoreManager.setBiometricConsent(consent)
        }
    }

    override suspend fun getBiometricConsent(): Boolean? {
        return withContext(Dispatchers.IO) {
            dataStoreManager.getBiometricConsent().firstOrNull()
        }
    }

}