package ru.vb.practice.domain

import ru.vb.practice.data.PinRepository

class ValidatePinUseCase(private val pinRepository: PinRepository) {
    suspend operator fun invoke(pin: String): Boolean{
        return pinRepository.validatePin(pin)
    }
}