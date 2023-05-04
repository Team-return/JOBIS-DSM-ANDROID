package team.retum.jobis_android.root

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import team.retum.jobis_android.feature.main.MainScreen
import team.retum.jobis_android.feature.signin.SignInScreen
import team.retum.jobis_android.feature.signup.SignUpScreen
import team.retum.jobis_android.root.navigation.JobisRoute
import team.retum.jobisui.colors.JobisColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            SetWindowStatus()

            val scaffoldState = rememberScaffoldState()
            val navController = rememberNavController()

            // TODO 토스트 시스템 구현
            Scaffold(
                scaffoldState = scaffoldState,
            ) {
                NavHost(
                    navController = navController,
                    startDestination = JobisRoute.SignIn,
                ) {

                    composable(
                        route = JobisRoute.SignUp,
                    ) {
                        SignUpScreen(
                            navController = navController,
                        )
                    }

                    composable(
                        route = JobisRoute.SignIn,
                    ) {
                        SignInScreen(
                            navController = navController,
                        )
                    }

                    composable(
                        route = JobisRoute.Main,
                    ) {
                        MainScreen()
                    }
                }
            }
        }
    }

    @Composable
    fun SetWindowStatus() {
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