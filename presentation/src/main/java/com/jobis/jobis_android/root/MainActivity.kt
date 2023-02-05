package com.jobis.jobis_android.root

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jobis.design_system.color.color
import com.jobis.jobis_android.JobisScreen
import com.jobis.jobis_android.feature.SplashScreen
import com.jobis.jobis_android.feature.auth.login.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            SetWindowStatus()

            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = JobisScreen.SPLASH.route,
            ){
                composable(
                    route = JobisScreen.SPLASH.route,
                ){
                    SplashScreen(
                        navController = navController,
                    )
                }
                composable(
                    route = JobisScreen.LOGIN.route,
                ){
                    LoginScreen(
                        navController = navController
                    )
                }
            }
        }
    }

    @Composable
    fun SetWindowStatus(){
        window.statusBarColor = color.Gray100.toArgb()
        window.navigationBarColor = color.Gray100.toArgb()

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