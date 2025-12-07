package com.example.recipeandroidapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.recipeandroidapp.presentation.navgraph.NavGraph
import com.example.recipeandroidapp.presentation.navgraph.Route
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.splashCondition
            }
        }
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                val isSysInDarkMode = isSystemInDarkTheme()
                val systemController = rememberSystemUiController()

                SideEffect {
                    systemController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = !isSysInDarkMode
                    )
                }
                NavGraph(startDestination = viewModel.startDestination)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavGraphPreview() {
    MaterialTheme {
        NavGraph(
            startDestination = Route.AppStartNavigation.route,
        )
    }
}


