package ru.vb.practice.domain

import kotlinx.coroutines.flow.Flow
import ru.vb.practice.data.PinRepository

class GetBiometricConsentUseCase(private val pinRepository: PinRepository) {
    suspend operator fun invoke(): Boolean? {
        return pinRepository.getBiometricConsent()
    }
}