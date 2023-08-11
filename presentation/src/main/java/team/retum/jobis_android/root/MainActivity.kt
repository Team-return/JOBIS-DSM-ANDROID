package team.retum.jobis_android.root

import android.annotation.SuppressLint
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
import androidx.compose.material.Scaffold
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
import dagger.hilt.android.AndroidEntryPoint
import team.retum.jobis_android.feature.auth.changepassword.ComparePasswordScreen
import team.retum.jobis_android.feature.auth.resetpassword.ResetPasswordScreen
import team.retum.jobis_android.feature.auth.resetpassword.ResetPasswordVerifyEmailScreen
import team.retum.jobis_android.feature.auth.signin.SignInScreen
import team.retum.jobis_android.feature.auth.signup.SignUpScreen
import team.retum.jobis_android.feature.bugreport.BugReportScreen
import team.retum.jobis_android.feature.company.CompaniesScreen
import team.retum.jobis_android.feature.company.CompanyDetailsScreen
import team.retum.jobis_android.feature.main.MainScreen
import team.retum.jobis_android.feature.recruitment.RecruitmentDetailsScreen
import team.retum.jobis_android.feature.recruitment.RecruitmentsScreen
import team.retum.jobis_android.feature.review.PostReviewScreen
import team.retum.jobis_android.feature.review.ReviewDetailsScreen
import team.retum.jobis_android.feature.splash.SplashScreen
import team.retum.jobis_android.root.navigation.JobisRoute
import team.retum.jobis_android.root.navigation.getPreviousDestination
import team.retum.jobis_android.root.navigation.navigatePopBackStack
import team.retum.jobis_android.root.navigation.navigateToCompanies
import team.retum.jobis_android.root.navigation.navigateToCompanyDetails
import team.retum.jobis_android.root.navigation.navigateToMain
import team.retum.jobis_android.root.navigation.navigateToMainWithPopUpSignIn
import team.retum.jobis_android.root.navigation.navigateToMyPage
import team.retum.jobis_android.root.navigation.navigateToRecruitmentDetails
import team.retum.jobis_android.root.navigation.navigateToRecruitments
import team.retum.jobis_android.root.navigation.navigateToResetPasswordVerifyEmail
import team.retum.jobis_android.root.navigation.navigateToReviewDetails
import team.retum.jobis_android.root.navigation.navigateToSignUp
import team.retum.jobis_android.root.navigation.putString
import team.retum.jobis_android.util.compose.animation.slideInLeft
import team.retum.jobis_android.util.compose.animation.slideInRight
import team.retum.jobis_android.util.compose.animation.slideOutLeft
import team.retum.jobis_android.util.compose.animation.slideOutRight
import team.retum.jobis_android.util.compose.component.JobisSnackBarHost
import team.retum.jobis_android.viewmodel.main.MainViewModel
import team.retum.jobis_android.viewmodel.resetpassword.ResetPasswordViewModel
import team.retum.jobis_android.viewmodel.signup.SignUpViewModel
import team.retum.jobisui.colors.JobisColor

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {

            SetWindowStatus()

            val signUpViewModel = hiltViewModel<SignUpViewModel>()

            val state = mainViewModel.container.stateFlow.collectAsState()

            val appState = rememberAppState()
            val navController = appState.navController

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

            Scaffold(scaffoldState = appState.scaffoldState, snackbarHost = {
                JobisSnackBarHost(appState)
            }) {
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
                        enterTransition = { slideInLeft() },
                        popExitTransition = { slideOutRight() },
                    ) {
                        SignUpScreen(
                            appState = appState,
                            signUpViewModel = signUpViewModel,
                            navigateToMain = navController::navigateToMain,
                            navigatePopBackStack = navController::navigatePopBackStack,
                        )
                    }

                    composable(
                        route = JobisRoute.SignIn,
                        exitTransition = { slideOutLeft() },
                        popEnterTransition = { slideInRight() },
                        popExitTransition = { slideOutRight() },
                    ) {
                        SignInScreen(
                            appState = appState,
                            navigateToMainWithPopUpSignIn = navController::navigateToMainWithPopUpSignIn,
                            navigateToResetPasswordVerifyEmail = navController::navigateToResetPasswordVerifyEmail,
                            navigateToSignUp = navController::navigateToSignUp,
                        )
                    }

                    composable(route = JobisRoute.Main, exitTransition = {
                        fadeOut()
                    }) {
                        MainScreen(
                            navigateToMyPage = navController::navigateToMyPage,
                            navigateToRecruitments = navController::navigateToRecruitments,
                            navigateToCompanies = navController::navigateToCompanies,
                        )
                    }

                    composable(route = JobisRoute.Recruitments,
                        exitTransition = { slideOutLeft() },
                        popEnterTransition = { slideInRight() },
                        popExitTransition = { fadeOut(tween(300)) }) {
                        RecruitmentsScreen(
                            putString = navController::putString,
                            navigateToRecruitmentDetails = navController::navigateToRecruitmentDetails,
                        )
                    }

                    composable(
                        route = JobisRoute.RecruitmentDetails + JobisRoute.RecruitmentId,
                        arguments = listOf(navArgument("recruitment-id") {
                            type = NavType.LongType
                        }),
                        enterTransition = { slideInLeft() },
                        exitTransition = { slideOutLeft() },
                        popEnterTransition = { slideInRight() },
                        popExitTransition = { slideOutRight() },
                    ) {
                        RecruitmentDetailsScreen(
                            recruitmentId = it.arguments?.getLong("recruitment-id") ?: 0L,
                            getPreviousDestination = navController::getPreviousDestination,
                            navigateToCompanyDetails = navController::navigateToCompanyDetails,
                        )
                    }

                    composable(
                        route = JobisRoute.Companies,
                        exitTransition = { slideOutLeft() },
                        popEnterTransition = { slideInRight() },
                        popExitTransition = { fadeOut(tween(300)) },
                    ) {
                        CompaniesScreen(navigateToCompanyDetails = navController::navigateToCompanyDetails)
                    }

                    composable(
                        route = "${JobisRoute.CompanyDetails}${JobisRoute.CompanyId}/${JobisRoute.HasRecruitment}",
                        arguments = listOf(navArgument("company-id") { type = NavType.IntType },
                            navArgument("has-recruitment") { type = NavType.BoolType }),
                        enterTransition = { slideInLeft() },
                        exitTransition = { slideOutLeft() },
                        popEnterTransition = { slideInRight() },
                        popExitTransition = { slideOutRight() },
                    ) {
                        CompanyDetailsScreen(
                            companyId = it.arguments?.getInt("company-id") ?: 0,
                            hasRecruitment = it.arguments?.getBoolean("has-recruitment") ?: false,
                            getPreviousDestination = navController::getPreviousDestination,
                            navigateToRecruitmentDetails = navController::navigateToRecruitmentDetails,
                            navigateToReviewDetails = navController::navigateToReviewDetails,
                        )
                    }

                    composable(
                        route = JobisRoute.ResetPasswordVerifyEmail,
                        enterTransition = { slideInLeft() },
                        exitTransition = { slideOutRight() },
                        popEnterTransition = { fadeIn(tween(300)) },
                        popExitTransition = { slideOutRight() },
                    ) {
                        ResetPasswordVerifyEmailScreen(
                            appState = appState,
                        )
                    }

                    val resetPasswordViewModel by viewModels<ResetPasswordViewModel>()

                    composable(
                        route = JobisRoute.ResetPassword,
                    ) {
                        ResetPasswordScreen(
                            appState = appState,
                            resetPasswordViewModel = resetPasswordViewModel,
                        )
                    }

                    composable(
                        route = JobisRoute.ComparePassword,
                    ) {
                        ComparePasswordScreen(
                            appState = appState,
                            resetPasswordViewModel = resetPasswordViewModel,
                        )
                    }

                    composable(
                        route = JobisRoute.MainNavigation.BugReport,
                    ) {
                        BugReportScreen()
                    }

                    composable(
                        route = "${JobisRoute.MainNavigation.ReviewDetails}${JobisRoute.ReviewId}",
                        arguments = listOf(navArgument("review-id") { type = NavType.StringType }),
                        enterTransition = { slideInLeft() },
                        exitTransition = { slideOutRight() },
                    ) {
                        ReviewDetailsScreen(
                            reviewId = it.arguments?.getString("review-id") ?: "",
                        )
                    }

                    composable(
                        route = JobisRoute.MainNavigation.PostReview,
                    ) {
                        PostReviewScreen(
                            appState = appState,
                        )
                    }
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

        @Suppress("DEPRECATION") if (MaterialTheme.colors.surface.luminance() > 0.5f) {
            window.decorView.systemUiVisibility =
                window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        @Suppress("DEPRECATION") if (MaterialTheme.colors.surface.luminance() > 0.5f) {
            window.decorView.systemUiVisibility =
                window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
    }
}