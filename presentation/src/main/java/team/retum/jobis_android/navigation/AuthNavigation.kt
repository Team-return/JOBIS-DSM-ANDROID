package team.retum.jobis_android.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import team.retum.jobis_android.feature.auth.changepassword.ComparePasswordScreen
import team.retum.jobis_android.feature.auth.resetpassword.ResetPasswordScreen
import team.retum.jobis_android.feature.auth.resetpassword.ResetPasswordVerifyEmailScreen
import team.retum.jobis_android.feature.auth.signin.SignInScreen
import team.retum.jobis_android.feature.auth.signup.SignUpScreen
import team.retum.jobis_android.util.compose.animation.slideInLeft
import team.retum.jobis_android.util.compose.animation.slideInRight
import team.retum.jobis_android.util.compose.animation.slideOutLeft
import team.retum.jobis_android.util.compose.animation.slideOutRight
import team.retum.jobis_android.util.compose.navigation.baseComposable
import team.retum.jobis_android.viewmodel.resetpassword.ResetPasswordViewModel
import team.retum.jobis_android.viewmodel.signup.SignUpViewModel

internal fun NavGraphBuilder.authNavigation(
    signUpViewModel: SignUpViewModel,
    resetPasswordViewModel: ResetPasswordViewModel,
    navigateToMainWithPopUpSignIn: () -> Unit,
    navigateToResetPasswordVerifyEmail: () -> Unit,
    navigateToSignUp: () -> Unit,
    navigateToMain: () -> Unit,
    navigatePopBackStack: () -> Unit,
    navigateToResetPassword: () -> Unit,
) {
    navigation(
        route = NavigationRoute.Auth,
        startDestination = AuthDestinations.SignIn,
    ) {
        baseComposable(
            route = AuthDestinations.SignIn,
            exitTransition = slideOutLeft(),
            popEnterTransition = slideInRight(),
            popExitTransition = slideOutRight(),
        ) {
            SignInScreen(
                navigateToMainWithPopUpSignIn = navigateToMainWithPopUpSignIn,
                navigateToResetPasswordVerifyEmail = navigateToResetPasswordVerifyEmail,
                navigateToSignUp = navigateToSignUp,
            )
        }

        baseComposable(
            route = AuthDestinations.SignUp,
            enterTransition = slideInLeft(),
            popExitTransition = slideOutRight(),
        ) {
            SignUpScreen(
                signUpViewModel = signUpViewModel,
                navigateToMain = navigateToMain,
                navigatePopBackStack = navigatePopBackStack,
            )
        }

        baseComposable(
            route = AuthDestinations.ResetPasswordVerifyEmail,
            enterTransition = slideInLeft(),
            exitTransition = slideOutRight(),
            popEnterTransition = fadeIn(tween(300)),
            popExitTransition = slideOutRight(),
        ) {
            ResetPasswordVerifyEmailScreen(navigateToResetPassword)
        }

        baseComposable(route = AuthDestinations.ResetPassword) {
            ResetPasswordScreen(
                resetPasswordViewModel = resetPasswordViewModel,
                navigateToMain = navigateToMain,
            )
        }

        baseComposable(route = AuthDestinations.ComparePassword) {
            ComparePasswordScreen(
                resetPasswordViewModel = resetPasswordViewModel,
                navigateToResetPassword = navigateToResetPassword,
            )
        }
    }
}