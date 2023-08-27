package team.retum.jobis_android.root

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.jobis.jobis_android.R
import dagger.hilt.android.AndroidEntryPoint
import team.retum.jobis_android.feature.auth.changepassword.ComparePasswordScreen
import team.retum.jobis_android.feature.auth.resetpassword.ResetPasswordScreen
import team.retum.jobis_android.feature.auth.resetpassword.ResetPasswordVerifyEmailScreen
import team.retum.jobis_android.feature.auth.signin.SignInScreen
import team.retum.jobis_android.feature.auth.signup.SignUpScreen
import team.retum.jobis_android.feature.bugreport.ReportBugScreen
import team.retum.jobis_android.feature.company.CompaniesScreen
import team.retum.jobis_android.feature.company.CompanyDetailsScreen
import team.retum.jobis_android.feature.main.MainScreen
import team.retum.jobis_android.feature.recruitment.RecruitmentDetailsScreen
import team.retum.jobis_android.feature.recruitment.RecruitmentsScreen
import team.retum.jobis_android.feature.review.PostReviewScreen
import team.retum.jobis_android.feature.review.ReviewDetailsScreen
import team.retum.jobis_android.feature.splash.SplashScreen
import team.retum.jobis_android.root.navigation.NavigationProperties
import team.retum.jobis_android.root.navigation.NavigationRoute
import team.retum.jobis_android.root.navigation.getArgument
import team.retum.jobis_android.root.navigation.getPreviousDestination
import team.retum.jobis_android.root.navigation.navigatePopBackStack
import team.retum.jobis_android.root.navigation.navigateToBugReport
import team.retum.jobis_android.root.navigation.navigateToCompanies
import team.retum.jobis_android.root.navigation.navigateToCompanyDetails
import team.retum.jobis_android.root.navigation.navigateToComparePassword
import team.retum.jobis_android.root.navigation.navigateToMain
import team.retum.jobis_android.root.navigation.navigateToMainWithPopUpSignIn
import team.retum.jobis_android.root.navigation.navigateToPostReview
import team.retum.jobis_android.root.navigation.navigateToRecruitmentDetails
import team.retum.jobis_android.root.navigation.navigateToRecruitments
import team.retum.jobis_android.root.navigation.navigateToResetPassword
import team.retum.jobis_android.root.navigation.navigateToResetPasswordVerifyEmail
import team.retum.jobis_android.root.navigation.navigateToReviewDetails
import team.retum.jobis_android.root.navigation.navigateToSignInPopUpWithMain
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
import team.returm.jobisdesignsystem.colors.JobisColor

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

            val backHandlerToastMessage = stringResource(id = R.string.back_handler)

            BackHandler {
                var waitTime = 0L
                if (System.currentTimeMillis() >= 1500) {
                    waitTime = System.currentTimeMillis()
                    Toast.makeText(this, backHandlerToastMessage, Toast.LENGTH_SHORT).show()
                } else {
                    navController.popBackStack()
                }

            }

            LaunchedEffect(Unit) {
                mainViewModel.fetchAutoSignInOption()
            }

            val moveToScreenBySignInOption = {
                navController.navigate(
                    if (state.value.autoSignInOption) NavigationRoute.Main
                    else NavigationRoute.SignIn,
                ) {
                    popUpTo(NavigationRoute.Splash) {
                        inclusive = true
                    }
                }
            }

            CompositionLocalProvider(LocalAppState provides appState) {
                Scaffold(
                    scaffoldState = appState.scaffoldState,
                    snackbarHost = { JobisSnackBarHost(appState) },
                ) {
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = NavigationRoute.Splash,
                    ) {
                        composable(
                            route = NavigationRoute.Splash,
                            exitTransition = { fadeOut(tween(300)) },
                        ) {
                            SplashScreen(moveToScreenBySignInOption = moveToScreenBySignInOption)
                        }

                        composable(
                            route = NavigationRoute.SignUp,
                            enterTransition = { slideInLeft() },
                            popExitTransition = { slideOutRight() },
                        ) {
                            SignUpScreen(
                                signUpViewModel = signUpViewModel,
                                navigateToMain = navController::navigateToMain,
                                navigatePopBackStack = navController::navigatePopBackStack,
                            )
                        }

                        composable(
                            route = NavigationRoute.SignIn,
                            exitTransition = { slideOutLeft() },
                            popEnterTransition = { slideInRight() },
                            popExitTransition = { slideOutRight() },
                        ) {
                            SignInScreen(
                                navigateToMainWithPopUpSignIn = navController::navigateToMainWithPopUpSignIn,
                                navigateToResetPasswordVerifyEmail = navController::navigateToResetPasswordVerifyEmail,
                                navigateToSignUp = navController::navigateToSignUp,
                            )
                        }

                        composable(
                            route = NavigationRoute.Main,
                            exitTransition = { fadeOut() },
                        ) {
                            MainScreen(
                                navigateToRecruitments = navController::navigateToRecruitments,
                                navigateToCompanies = navController::navigateToCompanies,
                                navigateToRecruitmentDetails = navController::navigateToRecruitmentDetails,
                                navigateToSignInPopUpWithMain = navController::navigateToSignInPopUpWithMain,
                                navigateToBugReport = navController::navigateToBugReport,
                                navigateToComparePassword = navController::navigateToComparePassword,
                                navigateToPostReview = navController::navigateToPostReview,
                            )
                        }

                        composable(
                            route = NavigationRoute.Recruitments,
                            exitTransition = { slideOutLeft() },
                            popEnterTransition = { slideInRight() },
                            popExitTransition = { fadeOut(tween(300)) },
                        ) {
                            RecruitmentsScreen(
                                putString = navController::putString,
                                navigateToRecruitmentDetails = navController::navigateToRecruitmentDetails,
                            )
                        }

                        composable(
                            route = NavigationRoute.RecruitmentDetails + NavigationRoute.RecruitmentId,
                            arguments = listOf(
                                getArgument(
                                    NavigationProperties.RECRUITMENT_ID,
                                    NavType.LongType,
                                ),
                            ),
                            enterTransition = { slideInLeft() },
                            exitTransition = { slideOutLeft() },
                            popEnterTransition = { slideInRight() },
                            popExitTransition = { slideOutRight() },
                        ) {
                            RecruitmentDetailsScreen(
                                recruitmentId = it.arguments?.getLong(NavigationProperties.RECRUITMENT_ID)
                                    ?: 0L,
                                getPreviousDestination = navController::getPreviousDestination,
                                navigateToCompanyDetails = navController::navigateToCompanyDetails,
                            )
                        }

                        composable(
                            route = NavigationRoute.Companies,
                            exitTransition = { slideOutLeft() },
                            popEnterTransition = { slideInRight() },
                            popExitTransition = { fadeOut(tween(300)) },
                        ) {
                            CompaniesScreen(navigateToCompanyDetails = navController::navigateToCompanyDetails)
                        }

                        composable(
                            route = "${NavigationRoute.CompanyDetails}${NavigationRoute.CompanyId}/${NavigationRoute.HasRecruitment}",
                            arguments = listOf(
                                getArgument(NavigationProperties.COMPANY_ID, NavType.LongType),
                                getArgument(NavigationProperties.HAS_RECRUITMENT, NavType.BoolType),
                            ),
                            enterTransition = { slideInLeft() },
                            exitTransition = { slideOutLeft() },
                            popEnterTransition = { slideInRight() },
                            popExitTransition = { slideOutRight() },
                        ) {
                            val companyId = it.arguments?.getLong(NavigationProperties.COMPANY_ID)
                            val hasRecruitment =
                                it.arguments?.getBoolean(NavigationRoute.HasRecruitment)
                            CompanyDetailsScreen(
                                companyId = companyId ?: 0,
                                hasRecruitment = hasRecruitment ?: false,
                                getPreviousDestination = navController::getPreviousDestination,
                                navigateToRecruitmentDetails = navController::navigateToRecruitmentDetails,
                                navigateToReviewDetails = navController::navigateToReviewDetails,
                            )
                        }

                        composable(
                            route = NavigationRoute.ResetPasswordVerifyEmail,
                            enterTransition = { slideInLeft() },
                            exitTransition = { slideOutRight() },
                            popEnterTransition = { fadeIn(tween(300)) },
                            popExitTransition = { slideOutRight() },
                        ) {
                            ResetPasswordVerifyEmailScreen(navigateToResetPassword = navController::navigateToResetPassword)
                        }

                        val resetPasswordViewModel by viewModels<ResetPasswordViewModel>()

                        composable(route = NavigationRoute.ResetPassword) {
                            ResetPasswordScreen(
                                navigateToMain = navController::navigateToMain,
                                resetPasswordViewModel = resetPasswordViewModel,
                            )
                        }

                        composable(route = NavigationRoute.ComparePassword) {
                            ComparePasswordScreen(
                                navigateToResetPassword = navController::navigateToResetPassword,
                                resetPasswordViewModel = resetPasswordViewModel,
                            )
                        }

                        composable(route = NavigationRoute.MainNavigation.ReportBug) {
                            ReportBugScreen()
                        }

                        composable(
                            route = "${NavigationRoute.MainNavigation.ReviewDetails}${NavigationRoute.ReviewId}",
                            arguments = listOf(
                                getArgument(NavigationProperties.REVIEW_ID, NavType.StringType),
                            ),
                            enterTransition = { slideInLeft() },
                            exitTransition = { slideOutRight() },
                        ) {
                            ReviewDetailsScreen(
                                reviewId = it.arguments?.getString(NavigationProperties.REVIEW_ID)
                                    ?: ""
                            )
                        }

                        composable(
                            route = NavigationRoute.MainNavigation.PostReview + NavigationRoute.CompanyId,
                            arguments = listOf(
                                getArgument(NavigationProperties.COMPANY_ID, NavType.LongType),
                            ),
                        ) {
                            val companyId = it.arguments?.getLong(NavigationProperties.COMPANY_ID)
                            PostReviewScreen(
                                companyId = companyId ?: 0,
                                navigatePopBackStack = navController::popBackStack,
                            )
                        }
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

@Stable
internal val LocalAppState = staticCompositionLocalOf<JobisAppState> {
    error("Not implemented")
}
