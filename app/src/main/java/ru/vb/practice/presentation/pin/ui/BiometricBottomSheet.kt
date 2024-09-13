package ru.vb.practice.presentation.pin.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.vb.practice.R
import ru.vb.practice.presentation.pin.PinIntent
import ru.vb.practice.presentation.pin.PinViewModel
import ru.vb.practice.ui.theme.SoftBlue
import ru.vb.practice.ui.theme.DarkGray
import ru.vb.practice.ui.theme.GeometriaMedium
import ru.vb.practice.ui.theme.RoyalBlue

@Composable
fun BiometricBottomSheet(
    onAccept: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(align = Alignment.BottomCenter)
            .background(Color.White, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .padding(top = 8.dp, start = 12.dp, end = 12.dp, bottom = 36.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(4.dp)
                    .background(color = SoftBlue, shape = RoundedCornerShape(2.dp))
            )
            Text(
                text = stringResource(id = R.string.use_face_id_question),
                color = Color.Black,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontFamily = GeometriaMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.use_face_id_info),
                color = DarkGray,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                fontFamily = GeometriaMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onAccept,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = RoyalBlue
                )
            ) {
                Text(
                    stringResource(id = R.string.use),
                    fontSize = 16.sp,
                    fontFamily = GeometriaMedium
                )
            }
        }
    }
}

