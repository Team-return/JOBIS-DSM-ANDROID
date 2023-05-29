package team.retum.jobis_android.feature.main

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import team.retum.jobis_android.feature.home.HomeScreen
import team.retum.jobis_android.feature.recruitment.SearchRecruitmentScreen
import team.retum.jobis_android.root.navigation.JobisRoute
import team.retum.jobis_android.util.compose.navigation.BottomBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
) {

    val scaffoldState = rememberScaffoldState()

    val navHostController = rememberNavController()

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomBar(
                navController = navController,
            )
        },
    ) {
        NavHost(
            navController = navHostController,
            startDestination = JobisRoute.Navigation.Home,
        ) {
            composable(
                route = JobisRoute.Navigation.Home,
            ) {
                HomeScreen(
                    navController = navController,
                )
            }
        }
    }
}