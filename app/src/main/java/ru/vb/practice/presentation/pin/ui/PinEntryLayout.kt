package ru.vb.practice.presentation.pin.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.vb.practice.R
import ru.vb.practice.presentation.pin.PinState
import ru.vb.practice.ui.theme.PastelGreen
import ru.vb.practice.ui.theme.DuskBlue
import ru.vb.practice.ui.theme.GeometriaMedium
import ru.vb.practice.ui.theme.WarmRed
import ru.vb.practice.ui.theme.RoyalBlue

@Composable
fun PinEntryLayout(pinCode: String, state: PinState) {
    val text = getPinStateText(state)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PinStateText(text = text)

        Spacer(modifier = Modifier.height(28.dp))

        PinIndicators(pinCode = pinCode, state = state)
    }
}

@Composable
private fun getPinStateText(state: PinState): String {
    return when (state) {
        is PinState.SettingPin -> stringResource(id = R.string.set_pin)
        is PinState.ConfirmingPin -> stringResource(id = R.string.repeat_pin)
        is PinState.PinMismatch -> stringResource(id = R.string.pin_mismatch)
        is PinState.AuthenticationSuccess, is PinState.NavigateToHome  -> stringResource(id = R.string.valid_pin)
        is PinState.PinSetSuccess -> stringResource(id = R.string.pin_set_success)
        is PinState.Initial -> stringResource(id = R.string.enter_pin)
        else -> stringResource(id = R.string.enter_pin)
    }
}

@Composable
private fun PinStateText(text: String) {
    Text(
        text = text,
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        fontFamily = GeometriaMedium
    )
}

@Composable
private fun PinIndicators(pinCode: String, state: PinState) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        repeat(4) { index ->
            val color = getPinIndicatorColor(index, pinCode, state)
            PinIndicator(color = color)
        }
    }
}

@Composable
private fun getPinIndicatorColor(index: Int, pinCode: String, state: PinState): Color {
    return when {
        state is PinState.AuthenticationSuccess || state is PinState.PinSetSuccess || state is PinState.NavigateToHome -> PastelGreen
        state is PinState.AuthenticationFailed || state is PinState.PinMismatch -> WarmRed
        index < pinCode.length -> RoyalBlue
        else -> DuskBlue
    }
}

@Composable
private fun PinIndicator(color: Color) {
    Box(
        modifier = Modifier
            .size(16.dp)
            .clip(CircleShape)
            .background(color)
    )
}
