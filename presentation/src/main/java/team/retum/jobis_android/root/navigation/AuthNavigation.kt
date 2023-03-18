package team.retum.jobis_android.root.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import team.retum.jobis_android.feature.auth.login.LoginScreen
import team.retum.jobis_android.root.SplashScreen

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