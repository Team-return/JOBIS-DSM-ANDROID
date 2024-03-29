package team.retum.jobis_android.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import team.retum.jobis_android.feature.auth.changepassword.ComparePasswordScreen
import team.retum.jobis_android.feature.auth.resetpassword.ResetPasswordScreen
import team.retum.jobis_android.feature.auth.resetpassword.ResetPasswordVerifyEmailScreen
import team.retum.jobis_android.feature.auth.resetpassword.ResetPasswordViewModel
import team.retum.jobis_android.feature.auth.signin.SignInScreen
import team.retum.jobis_android.feature.auth.signup.SignUpScreen
import team.retum.jobis_android.util.compose.animation.slideInLeft
import team.retum.jobis_android.util.compose.animation.slideInRight
import team.retum.jobis_android.util.compose.animation.slideOutLeft
import team.retum.jobis_android.util.compose.animation.slideOutRight
import team.retum.jobis_android.util.compose.navigation.baseComposable

internal fun NavGraphBuilder.authNavigation(
    resetPasswordViewModel: ResetPasswordViewModel,
    navigateToMainWithPopUpSignIn: () -> Unit,
    navigateToResetPasswordVerifyEmail: () -> Unit,
    navigateToSignUp: () -> Unit,
    navigateToMain: () -> Unit,
    navigatePopBackStack: () -> Unit,
    navigateToResetPassword: () -> Unit,
    navigateToSignIn: () -> Unit,
    getPreviousDestination: () -> String?,
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
                navigateToMain = navigateToMain,
                navigatePopBackStack = navigatePopBackStack,
            )
        }

        baseComposable(
            route = AuthDestinations.ResetPasswordVerifyEmail,
            enterTransition = slideInLeft(),
            exitTransition = slideOutLeft(),
            popEnterTransition = slideInRight(),
            popExitTransition = slideOutRight(),
        ) {
            ResetPasswordVerifyEmailScreen(
                navigateToResetPassword = navigateToResetPassword,
                resetPasswordViewModel = resetPasswordViewModel,
            )
        }

        baseComposable(
            route = AuthDestinations.ResetPassword,
            enterTransition = slideInLeft(),
            popExitTransition = slideOutRight(),
        ) {
            ResetPasswordScreen(
                getPreviousDestination = getPreviousDestination,
                resetPasswordViewModel = resetPasswordViewModel,
                navigateToSignIn = navigateToSignIn,
                navigateToMain = navigateToMain,
            )
        }

        baseComposable(
            route = AuthDestinations.ComparePassword,
            exitTransition = slideOutLeft(),
            popEnterTransition = slideInRight(),
        ) {
            ComparePasswordScreen(
                resetPasswordViewModel = resetPasswordViewModel,
                navigateToResetPassword = navigateToResetPassword,
            )
        }
    }
}
