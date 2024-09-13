package ru.vb.practice.domain

import ru.vb.practice.data.PinRepository

class GetPinUseCase(private val pinRepository: PinRepository) {
    suspend operator fun invoke(): String? {
        return pinRepository.getPin()
    }
}