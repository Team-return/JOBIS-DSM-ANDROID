package team.retum.jobis_android.root

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import team.retum.jobis_android.feature.auth.changepassword.ComparePasswordScreen
import team.retum.jobis_android.feature.auth.resetpassword.ResetPasswordScreen
import team.retum.jobis_android.feature.auth.resetpassword.ResetPasswordVerifyEmailScreen
import team.retum.jobis_android.feature.auth.signin.SignInScreen
import team.retum.jobis_android.feature.auth.signup.SignUpScreen
import team.retum.jobis_android.feature.company.CompaniesScreen
import team.retum.jobis_android.feature.company.CompanyDetailsScreen
import team.retum.jobis_android.feature.main.MainScreen
import team.retum.jobis_android.feature.recruitment.RecruitmentDetailsScreen
import team.retum.jobis_android.feature.recruitment.RecruitmentsScreen
import team.retum.jobis_android.feature.splash.SplashScreen
import team.retum.jobis_android.root.navigation.JobisRoute
import team.retum.jobis_android.util.compose.slideInLeft
import team.retum.jobis_android.util.compose.slideInRight
import team.retum.jobis_android.util.compose.slideOutLeft
import team.retum.jobis_android.util.compose.slideOutRight
import team.retum.jobis_android.viewmodel.main.MainViewModel
import team.retum.jobis_android.viewmodel.signup.SignUpViewModel
import team.retum.jobisui.colors.JobisColor

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {

            SetWindowStatus()

            val navController = rememberAnimatedNavController()

            val signUpViewModel = hiltViewModel<SignUpViewModel>()

            val state = mainViewModel.container.stateFlow.collectAsState()

            LaunchedEffect(Unit) {
                mainViewModel.fetchAutoSignInOption()
            }

            val moveToScreenBySignInOption = {
                navController.navigate(
                    if (state.value.autoSignInOption) JobisRoute.Main
                    else JobisRoute.SignIn,
                ) {
                    popUpTo(JobisRoute.Splash) {
                        inclusive = true
                    }
                }
            }

            // TODO 토스트 시스템 구현
            AnimatedNavHost(
                navController = navController,
                startDestination = JobisRoute.Splash,
            ) {
                composable(
                    route = JobisRoute.Splash,
                    exitTransition = { fadeOut(tween(300)) },
                ) {
                    SplashScreen(moveToScreenBySignInOption = moveToScreenBySignInOption)
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
                    exitTransition = { fadeOut(tween(500)) },
                ) {
                    SignInScreen(
                        navController = navController,
                    )
                }

                composable(
                    route = JobisRoute.Main,
                    exitTransition = {
                        fadeOut()
                    }
                ) {
                    MainScreen(
                        navController = navController,
                    )
                }

                composable(
                    route = JobisRoute.Recruitments,
                    exitTransition = { slideOutLeft() },
                    popEnterTransition = { slideInRight() },
                    popExitTransition = { fadeOut(tween(300)) }
                ) {
                    RecruitmentsScreen(
                        navController = navController,
                    )
                }

                composable(
                    route = JobisRoute.RecruitmentDetails,
                    arguments = listOf(
                        navArgument("recruitment-id") { type = NavType.LongType }
                    ),
                    enterTransition = { slideInLeft() },
                    exitTransition = { slideOutLeft() },
                    popEnterTransition = { slideInRight() },
                    popExitTransition = { slideOutRight() }
                ) {
                    RecruitmentDetailsScreen(
                        navController = navController,
                        recruitmentId = it.arguments?.getLong("recruitment-id") ?: 0L,
                    )
                }

                composable(
                    route = JobisRoute.Companies,
                    exitTransition = { slideOutLeft() },
                    popEnterTransition = { slideInRight() },
                    popExitTransition = { fadeOut(tween(300)) }
                ) {
                    CompaniesScreen(
                        navController = navController,
                    )
                }

                composable(
                    route = JobisRoute.CompanyDetails,
                    arguments = listOf(
                        navArgument("company-id") { type = NavType.IntType },
                        navArgument("has-recruitment") { type = NavType.BoolType }
                    ),
                    enterTransition = { slideInLeft() },
                    exitTransition = { slideOutLeft() },
                    popEnterTransition = { slideInRight() },
                    popExitTransition = { slideOutRight() }
                ) {
                    CompanyDetailsScreen(
                        navController = navController,
                        companyId = it.arguments?.getInt("company-id") ?: 0,
                        hasRecruitment = it.arguments?.getBoolean("has-recruitment") ?: false,
                    )
                }

                composable(
                    route = JobisRoute.ResetPasswordVerifyEmail,
                    enterTransition = { fadeIn(tween(300)) },
                    exitTransition = { fadeOut(tween(300)) },
                    popEnterTransition = { fadeIn(tween(300)) },
                    popExitTransition = { fadeOut(tween(300)) },
                ) {
                    ResetPasswordVerifyEmailScreen(
                        navController = navController,
                    )
                }

                composable(
                    route = JobisRoute.ResetPassword,
                ) {
                    ResetPasswordScreen(navController = navController)
                }

                composable(
                    route = JobisRoute.ComparePassword,
                ){
                    ComparePasswordScreen(navController = navController)
                }
            }
        }
    }

    @Composable
    private fun SetWindowStatus() {
        window.statusBarColor = JobisColor.Gray100.toArgb()
        window.navigationBarColor = JobisColor.Gray100.toArgb()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

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