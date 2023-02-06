package com.jobis.jobis_android.root.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jobis.jobis_android.root.SplashScreen
import com.jobis.jobis_android.feature.auth.login.LoginScreen

fun NavGraphBuilder.authNavigation(
    navController: NavController,
) {
    navigation(
        startDestination = JobisScreen.Auth.SPLASH,
        route = JobisRoute.Auth.route,
    ) {
        composable(
            route = JobisScreen.Auth.SPLASH,
        ) {
            SplashScreen(
                navController = navController,
            )
        }

        composable(
            route = JobisScreen.Auth.LOGIN,
        ) {
            LoginScreen(
                navController = navController,
            )
        }
    }
}