package ru.vb.practice.domain

import ru.vb.practice.data.PinRepository

class SetPinUseCase(private val pinRepository: PinRepository) {
    suspend operator fun invoke(pin: String){
        pinRepository.savePin(pin)
    }
}