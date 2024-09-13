package ru.vb.practice.presentation.pin.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.biometric.BiometricManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import ru.vb.practice.R
import ru.vb.practice.navigation.Screen
import ru.vb.practice.presentation.pin.PinIntent
import ru.vb.practice.presentation.pin.PinState
import ru.vb.practice.presentation.pin.PinViewModel
import ru.vb.practice.ui.theme.GeometriaMedium
import ru.vb.practice.ui.theme.LocalSpacing
import ru.vb.practice.ui.theme.RoyalBlue
import ru.vb.practice.ui.theme.spacing


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PinScreen(
    navController: NavController = rememberNavController(),
    pinViewModel: PinViewModel = koinViewModel(),
    context: FragmentActivity
) {
    val viewState by pinViewModel.viewState.collectAsState()
    val sheetState = rememberModalBottomSheetState()

    // Инициализация биометрии при первом запуске
    LaunchedEffect(viewState.isInitialized) {
        pinViewModel.showBottomSheetOrUseBiometric(context)
    }

    // Обработка ввода пин-кода
    LaunchedEffect(viewState.pinCode) {
        if (viewState.pinCode.length == 4) {
            pinViewModel.handleIntent(PinIntent.ChangePinCode(viewState.pinCode))
        }
    }

    // Обработка состояний пин-кода
    LaunchedEffect(viewState.state) {
        pinViewModel.handlePinState()
    }

    LaunchedEffect(viewState.state) {
        if (viewState.state == PinState.NavigateToHome) {
            navigateToHome(navController)
        }
    }

    // Обработка нажатия кнопки "Назад"
    BackHandler {
        if (viewState.state is PinState.ConfirmingPin) {
            pinViewModel.handleIntent(PinIntent.Reset)
        } else {
            navController.popBackStack()
        }
    }

    // Отображение основного экрана с вводом пин-кода и клавиатурой
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(it)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                    Logo()
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                    PinEntryLayout(viewState.pinCode, viewState.state)
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                    NumberPad(
                        pinViewModel,
                        viewState.isPinSet,
                        viewState.isBiometricConsent,
                        viewState.isInputBlocked,
                        context,
                        viewState.canUseBiometric,
                    )
                    Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
                    TextCantSignIn()
                }
            }
        }
    )

    // Отображение модального нижнего листа для биометрической аутентификации
    if (viewState.isShowBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                pinViewModel.changeShowBottomSheet(false)
                pinViewModel.handleIntent(PinIntent.SetBiometricConsent(false))
            },
            sheetState = sheetState,
            modifier = Modifier.wrapContentHeight(),
            dragHandle = {}
        ) {
            BiometricBottomSheet(
                onAccept = {
                    pinViewModel.changeShowBottomSheet(false)
                    pinViewModel.setBiometricConsent(true)
                    pinViewModel.authenticateBiometric(context)
                }
            )
        }
    }
}

// Функция для навигации на экран домашнего меню
private fun navigateToHome(navController: NavController) {
    navController.navigate(Screen.HOME_SCREEN) {
        popUpTo(Screen.PIN_SCREEN) {
            inclusive = true
        }
    }
}

// Компонент для отображения логотипа
@Composable
private fun Logo() {
    Image(
        painter = painterResource(id = R.drawable.ic_logo_zabot),
        contentDescription = "logo"
    )
}

@Composable
fun TextCantSignIn() {
    Box(
        modifier = Modifier
            .clickable {  }
            .sizeIn(minWidth = 48.dp, minHeight = 48.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.cant_sign_in),
            fontSize = 16.sp,
            color = RoyalBlue,
            fontFamily = GeometriaMedium
        )
    }
}
