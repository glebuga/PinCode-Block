package ru.vb.practice.di

import androidx.fragment.app.FragmentActivity
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.vb.practice.data.DataStoreManager
import ru.vb.practice.data.PinRepository
import ru.vb.practice.data.PinRepositoryImpl
import ru.vb.practice.domain.GetBiometricConsentUseCase
import ru.vb.practice.domain.GetPinUseCase
import ru.vb.practice.domain.SetBiometricConsentUseCase
import ru.vb.practice.domain.SetPinUseCase
import ru.vb.practice.domain.ValidatePinUseCase
import ru.vb.practice.presentation.home.HomeViewModel
import ru.vb.practice.presentation.pin.PinViewModel

val appModule = module {
    single { DataStoreManager(get()) }
    single<PinRepository> { PinRepositoryImpl(get()) }
    single { SetPinUseCase(get()) }
    single { GetPinUseCase(get()) }
    single { ValidatePinUseCase(get()) }
    single { SetBiometricConsentUseCase(get()) }
    single { GetBiometricConsentUseCase(get()) }
    viewModel { PinViewModel(get(), get(), get(), get(), get()) }
    viewModel { HomeViewModel() }
}