package ru.vb.practice.domain

import ru.vb.practice.data.PinRepository

class SetBiometricConsentUseCase(private val pinRepository: PinRepository) {
    suspend operator fun invoke(consent: Boolean) {
        pinRepository.setBiometricConsent(consent)
    }
}