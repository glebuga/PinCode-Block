package ru.vb.practice.data

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.security.MessageDigest

val Context.dataStore by preferencesDataStore("settings")
class DataStoreManager(private val context: Context) {

    companion object{
        private val pinKey = stringPreferencesKey("pin_key")
        private val biometricConsentKey = booleanPreferencesKey("biometric_consent")
    }

    @SuppressLint("SuspiciousIndentation")
    suspend fun savePin(pin: String) {
        val hashedPin = hashPin(pin)
            context.dataStore.edit { settings ->
                settings[pinKey] = hashedPin
            }
    }

    fun getPin(): Flow<String?> {
        return context.dataStore.data.map { settings ->
            settings[pinKey]
        }
    }

    suspend fun setBiometricConsent(consent: Boolean) {
        context.dataStore.edit { settings ->
            settings[biometricConsentKey] = consent
        }
    }

    fun getBiometricConsent(): Flow<Boolean?> {
        return context.dataStore.data.map { settings ->
            settings[biometricConsentKey]
        }
    }

    internal fun hashPin(pin: String): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(pin.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }
}