package team.retum.jobis_android.root

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import team.retum.jobis_android.feature.auth.login.LoginScreen
import team.retum.jobis_android.feature.home.HomeScreen
import team.retum.jobis_android.root.navigation.JobisRoute
import team.retum.jobisui.colors.JobisColor

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            SetWindowStatus()

            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = JobisRoute.Login,
            ) {
                composable(
                    route = JobisRoute.Login,
                ) {
                    LoginScreen(
                        navController = navController,
                    )
                }

                composable(
                    route = JobisRoute.Home,
                ){
                    HomeScreen(
                        navController = navController,
                    )
                }
            }
        }
    }

    @Composable
    fun SetWindowStatus() {
        window.statusBarColor = JobisColor.Gray100.toArgb()
        window.navigationBarColor = JobisColor.Gray100.toArgb()

        @Suppress("DEPRECATION")
        if (MaterialTheme.colors.surface.luminance() > 0.5f) {
            window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        @Suppress("DEPRECATION")
        if (MaterialTheme.colors.surface.luminance() > 0.5f) {
            window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
                    View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
    }
}