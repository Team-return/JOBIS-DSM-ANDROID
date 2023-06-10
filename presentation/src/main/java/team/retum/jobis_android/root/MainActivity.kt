package team.retum.jobis_android.root

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import team.retum.jobis_android.feature.company.CompanyDetailsScreen
import team.retum.jobis_android.feature.company.CompaniesScreen
import team.retum.jobis_android.feature.main.MainScreen
import team.retum.jobis_android.feature.recruitment.RecruitmentDetailsScreen
import team.retum.jobis_android.feature.recruitment.RecruitmentsScreen
import team.retum.jobis_android.feature.signin.SignInScreen
import team.retum.jobis_android.feature.signup.SignUpScreen
import team.retum.jobis_android.feature.splash.SplashScreen
import team.retum.jobis_android.root.navigation.JobisRoute
import team.retum.jobis_android.viewmodel.main.MainViewModel
import team.retum.jobis_android.viewmodel.signup.SignUpViewModel
import team.retum.jobisui.colors.JobisColor

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {

            SetWindowStatus()

            val navController = rememberNavController()

            val signUpViewModel = hiltViewModel<SignUpViewModel>()

            val state = mainViewModel.container.stateFlow.collectAsState()

            val moveToScreenBySignInOption = {
                navController.navigate(
                    if (state.value.autoSignInOption) JobisRoute.Main
                    else JobisRoute.SignIn,
                ){
                    popUpTo(JobisRoute.Splash){
                        inclusive = true
                    }
                }
            }

            // TODO 토스트 시스템 구현
            NavHost(
                navController = navController,
                startDestination = JobisRoute.Splash,
            ) {

                composable(
                    route = JobisRoute.Splash,
                ) {
                    SplashScreen(
                        moveToScreenBySignInOption = moveToScreenBySignInOption,
                        mainViewModel = mainViewModel,
                    )
                }

                composable(
                    route = JobisRoute.SignUp,
                ) {
                    SignUpScreen(
                        navHostController = navController,
                        signUpViewModel = signUpViewModel,
                        moveToSignIn = {
                            navController.popBackStack()
                        }
                    ) {
                        navController.navigate(JobisRoute.Main)
                    }
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
                    MainScreen(
                        navController = navController,
                    )
                }

                composable(
                    route = JobisRoute.Recruitments,
                ) {
                    RecruitmentsScreen(
                        navController = navController,
                    )
                }

                composable(
                    route = JobisRoute.RecruitmentDetails,
                    arguments = listOf(
                        navArgument("recruitment-id") { type = NavType.LongType }
                    )
                ) {
                    RecruitmentDetailsScreen(
                        navController = navController,
                        recruitmentId = it.arguments?.getLong("recruitment-id") ?: 0L,
                    )
                }

                composable(
                    route = JobisRoute.Company,
                ) {
                    CompaniesScreen(
                        navController = navController,
                    )
                }

                composable(
                    route = JobisRoute.CompanyDetails,
                    arguments = listOf(
                        navArgument("company-id") { type = NavType.IntType }
                    )
                ) {
                    CompanyDetailsScreen(
                        navController = navController,
                        companyId = it.arguments?.getInt("company-id") ?: 0,
                    )
                }
            }
        }
    }

    @Composable
    private fun SetWindowStatus() {
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