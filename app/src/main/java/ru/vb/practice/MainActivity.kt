package ru.vb.practice

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.rememberNavController
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.parametersOf
import ru.vb.practice.navigation.NavGraph

class MainActivity : FragmentActivity() {
//    private val biometricManager: BiometricManager by lazy {
//        getKoin().get { parametersOf(this) }
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            NavGraph(navController, this)
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        biometricManager.clearCallback()
//    }
}
