package ru.vb.practice.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.vb.practice.presentation.home.ui.HomeScreen
import ru.vb.practice.presentation.pin.ui.PinScreen
import ru.vb.practice.ui.theme.LocalSpacing
import ru.vb.practice.ui.theme.Practice2024Theme
import ru.vb.practice.ui.theme.ProvideSpacing
import ru.vb.practice.ui.theme.Spacing

@Composable
fun NavGraph(navController: NavHostController, context: FragmentActivity) {
    ProvideSpacing(LocalSpacing provides Spacing()) {
        Practice2024Theme {
            NavHost(navController = navController, startDestination = Screen.PIN_SCREEN) {
                composable(Screen.PIN_SCREEN) {
                    PinScreen(navController = navController, context = context)
                }
                composable(Screen.HOME_SCREEN) {
                    HomeScreen()
                }
            }
        }
    }
}