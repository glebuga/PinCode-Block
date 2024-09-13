package ru.vb.practice.presentation.pin.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import ru.vb.practice.R
import ru.vb.practice.presentation.pin.PinViewModel
import ru.vb.practice.ui.theme.GeometriaMedium
import ru.vb.practice.ui.theme.SoftBlue

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun NumberPad(
    pinViewModel: PinViewModel,
    isPinSet: Boolean,
    isBiometricConsent: Boolean?,
    isInputBlocked: Boolean,
    context: FragmentActivity,
    canUseBiometric: Boolean,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Цифровая клавиатура
        NumberPadGrid(
            pinViewModel = pinViewModel,
            isInputBlocked = isInputBlocked
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            // Кнопка биометрической аутентификации (если доступно)
            BiometricButton(
                canUseBiometric = canUseBiometric,
                isInputBlocked = isInputBlocked,
                isPinSet = isPinSet,
                isBiometricConsent = isBiometricConsent,
                context = context,
                pinViewModel = pinViewModel
            )
            // Кнопка "0"
            NumberButton(
                number = "0",
                fontFamily = GeometriaMedium,
                onClick = {
                    if (!isInputBlocked) {
                        pinViewModel.addDigit("0")
                    }
                }
            )
            // Кнопка удаления последней цифры
            DeleteButton(
                pinViewModel = pinViewModel,
                isInputBlocked = isInputBlocked
            )
        }

    }
}

@Composable
fun NumberPadGrid(
    pinViewModel: PinViewModel,
    isInputBlocked: Boolean
) {
    for (i in 1..3) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            for (j in 1..3) {
                val number = (i - 1) * 3 + j
                NumberButton(
                    number = number.toString(),
                    fontFamily = GeometriaMedium,
                    onClick = {
                        if (!isInputBlocked) {
                            pinViewModel.addDigit(number.toString())
                        }
                    }
                )
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun BiometricButton(
    canUseBiometric: Boolean,
    isInputBlocked: Boolean,
    isPinSet: Boolean,
    isBiometricConsent: Boolean?,
    context: FragmentActivity,
    pinViewModel: PinViewModel,

    ) {
    if (canUseBiometric && isPinSet && isBiometricConsent == true) {
        Button(
            onClick = {
                if (!isInputBlocked) {
                    pinViewModel.authenticateBiometric(context)
                }
            },
            modifier = Modifier.size(64.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black,
                containerColor = Color.Transparent
            ),
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_face_id),
                    contentDescription = stringResource(id = R.string.face_id_description)
                )
            }
        }
    } else {
        Spacer(modifier = Modifier.size(64.dp))
    }
}

@Composable
fun DeleteButton(
    pinViewModel: PinViewModel,
    isInputBlocked: Boolean
) {
    Button(
        onClick = {
            if (!isInputBlocked) {
                pinViewModel.deleteLastDigit()
            }
        },
        modifier = Modifier.size(64.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.Black,
            containerColor = Color.Transparent
        ),
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = stringResource(id = R.string.delete_last_digit),
            )
        }
    }
}

@Composable
fun NumberButton(number: String, fontFamily: FontFamily, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(64.dp),
        border = BorderStroke(1.dp, SoftBlue),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.Black,
            containerColor = Color.White
        )
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize(align = Alignment.Center)
        ) {
            Text(
                text = number,
                fontSize = 30.sp,
                fontFamily = fontFamily,
                textAlign = TextAlign.Center
            )
        }
    }
}