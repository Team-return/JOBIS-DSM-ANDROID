package team.retum.jobis_android.root.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import team.retum.jobis_android.feature.home.HomeScreen

fun NavGraphBuilder.homeNavigation(
    navController: NavController,
){
    navigation(
        startDestination = JobisScreen.Home.HOME,
        route = JobisRoute.Home.route
    ){
        composable(
            route = JobisScreen.Home.HOME,
        ){
            HomeScreen(
                navController = navController,
            )
        }
    }
}