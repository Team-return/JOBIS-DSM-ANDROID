package team.retum.jobis_android.root

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import team.retum.jobis_android.feature.auth.SplashScreen
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
                startDestination = JobisRoute.Splash,
            ) {

                composable(
                    route = JobisRoute.Splash,
                ){
                    SplashScreen(
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
    fun SetWindowStatus(
        statusBarColor: Color = JobisColor.Gray100,
        navigationBarColor: Color = JobisColor.Gray100,
    ) {
        window.statusBarColor = JobisColor.DarkBlue.toArgb()
        window.navigationBarColor = JobisColor.LightBlue.toArgb()

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