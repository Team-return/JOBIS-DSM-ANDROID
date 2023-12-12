package team.retum.jobis_android

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import team.retum.jobis_android.feature.auth.resetpassword.ResetPasswordViewModel
import team.retum.jobis_android.feature.auth.signup.SignUpViewModel
import team.retum.jobis_android.feature.root.RootScreen
import team.retum.jobis_android.feature.splash.SplashScreen
import team.retum.jobis_android.navigation.AuthDestinations
import team.retum.jobis_android.navigation.NavigationRoute
import team.retum.jobis_android.navigation.authNavigation
import team.retum.jobis_android.navigation.getPreviousDestination
import team.retum.jobis_android.navigation.getString
import team.retum.jobis_android.navigation.mainNavigation
import team.retum.jobis_android.navigation.navigatePopBackStack
import team.retum.jobis_android.navigation.navigateToBugReport
import team.retum.jobis_android.navigation.navigateToCompanies
import team.retum.jobis_android.navigation.navigateToCompanyDetails
import team.retum.jobis_android.navigation.navigateToComparePassword
import team.retum.jobis_android.navigation.navigateToNotifications
import team.retum.jobis_android.navigation.navigateToPostReview
import team.retum.jobis_android.navigation.navigateToRecruitmentDetails
import team.retum.jobis_android.navigation.navigateToRecruitments
import team.retum.jobis_android.navigation.navigateToResetPassword
import team.retum.jobis_android.navigation.navigateToResetPasswordVerifyEmail
import team.retum.jobis_android.navigation.navigateToReviewDetails
import team.retum.jobis_android.navigation.navigateToRoot
import team.retum.jobis_android.navigation.navigateToRootWithPopUpSignIn
import team.retum.jobis_android.navigation.navigateToSignInPopUpWithMain
import team.retum.jobis_android.navigation.navigateToSignUp
import team.retum.jobis_android.navigation.putString
import team.retum.jobis_android.navigation.userNavigation

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun JobisApp(
    signInOption: Boolean,
    signUpViewModel: SignUpViewModel,
    resetPasswordViewModel: ResetPasswordViewModel,
) {
    val navController = rememberAnimatedNavController()

    val navRouteBySignInOption = if (signInOption) {
        NavigationRoute.Root
    } else {
        AuthDestinations.SignIn
    }

    val moveToScreenBySignInOption = {
        navController.navigate(navRouteBySignInOption) {
            popUpTo(NavigationRoute.Splash) {
                inclusive = true
            }
        }
    }

    AnimatedNavHost(
        modifier = Modifier.navigationBarsPadding(),
        navController = navController,
        startDestination = NavigationRoute.Splash,
    ) {
        authNavigation(
            signUpViewModel = signUpViewModel,
            resetPasswordViewModel = resetPasswordViewModel,
            navigateToMainWithPopUpSignIn = navController::navigateToRootWithPopUpSignIn,
            navigateToResetPasswordVerifyEmail = navController::navigateToResetPasswordVerifyEmail,
            navigateToSignUp = navController::navigateToSignUp,
            navigateToMain = navController::navigateToRoot,
            navigatePopBackStack = navController::navigatePopBackStack,
            navigateToResetPassword = navController::navigateToResetPassword,
            navigateToSignIn = navController::navigateToSignInPopUpWithMain,
            getPreviousDestination = navController::getPreviousDestination,
        )

        mainNavigation(
            putString = navController::putString,
            getString = navController::getString,
            navigateToRecruitmentDetails = navController::navigateToRecruitmentDetails,
            getPreviousDestination = navController::getPreviousDestination,
            navigateToCompanyDetails = navController::navigateToCompanyDetails,
            navigateToReviewDetails = navController::navigateToReviewDetails,
            popBackStack = navController::popBackStack,
        )

        userNavigation(navController::popBackStack)

        composable(
            route = NavigationRoute.Splash,
            exitTransition = { fadeOut(tween(300)) },
        ) {
            SplashScreen(moveToScreenBySignInOption = moveToScreenBySignInOption)
        }

        composable(
            route = NavigationRoute.Root,
            exitTransition = { fadeOut() },
        ) {
            RootScreen(
                navigateToRecruitments = navController::navigateToRecruitments,
                navigateToCompanies = navController::navigateToCompanies,
                navigateToRecruitmentDetails = navController::navigateToRecruitmentDetails,
                navigateToSignInPopUpWithMain = navController::navigateToSignInPopUpWithMain,
                navigateToBugReport = navController::navigateToBugReport,
                navigateToComparePassword = navController::navigateToComparePassword,
                navigateToPostReview = navController::navigateToPostReview,
                navigateToNotifications = navController::navigateToNotifications,
            )
        }
    }
}
