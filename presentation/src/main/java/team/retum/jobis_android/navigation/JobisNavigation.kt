package team.retum.jobis_android.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument

internal fun NavHostController.navigateToMain() {
    this.navigate(NavigationRoute.Root) {
        popUpTo(NavigationRoute.Root) {
            inclusive = true
        }
    }
}

internal fun NavHostController.navigateToMainWithPopUpSignIn() {
    this.navigate(NavigationRoute.Root) {
        popUpTo(AuthDestinations.SignIn) {
            inclusive = true
        }
    }
}

internal fun NavHostController.navigateToSignInPopUpWithMain() {
    this.navigate(AuthDestinations.SignIn) {
        popUpTo(NavigationRoute.Root) {
            inclusive = true
        }
    }
}

internal fun NavHostController.navigatePopBackStack() {
    this.popBackStack()
}

internal fun NavHostController.navigateToResetPasswordVerifyEmail() {
    this.navigate(AuthDestinations.ResetPasswordVerifyEmail)
}

internal fun NavHostController.navigateToSignUp() {
    this.navigate(AuthDestinations.SignUp)
}

internal fun NavHostController.navigateToMyPage() {
    this.navigate(NavigationRoute.BottomNavigation.MyPage)
}

internal fun NavHostController.navigateToRecruitments() {
    this.navigate(MainDestinations.Recruitments)
}

internal fun NavHostController.navigateToCompanies() {
    this.navigate(MainDestinations.Companies)
}

internal fun NavHostController.navigateToRecruitmentDetails(
    recruitmentId: Long?,
) {
    this.navigate(MainDestinations.RecruitmentDetails + recruitmentId)
}

internal fun NavHostController.navigateToBugReport() {
    this.navigate(UserDestination.ReportBug)
}

internal fun NavHostController.navigateToComparePassword() {
    this.navigate(AuthDestinations.ComparePassword)
}

internal fun NavHostController.navigateToBookmarkRecruitments() {
    this.navigate(NavigationRoute.BottomNavigation.BookmarkRecruitments)
}

internal fun NavHostController.putString(
    key: String,
    value: String,
) {
    this.currentBackStackEntry?.arguments?.putString(key, value)
}

internal fun NavHostController.getPreviousDestination(): String? {
    return this.previousBackStackEntry?.destination?.route
}

internal fun NavHostController.navigateToCompanyDetails(companyId: Long) {
    this.navigate("${MainDestinations.CompanyDetails}${companyId}")
}

internal fun NavHostController.navigateToVerifyEmail(){
    this.navigate(AuthDestinations.VerifyEmail)
}

internal fun NavHostController.navigateToSetPassword(){
    this.navigate(AuthDestinations.SetPassword)
}

internal fun NavHostController.navigateToReviewDetails(
    reviewId: String,
) {
    this.navigate("${MainDestinations.ReviewDetails}${reviewId}")
}

internal fun NavHostController.navigateToResetPassword() {
    this.navigate(AuthDestinations.ResetPassword)
}

internal fun <T> getArgument(
    name: String,
    type: NavType<T>,
): NamedNavArgument {
    return navArgument(name) {
        this.type = type
    }
}

internal fun NavHostController.navigateToPostReview(companyId: Long) {
    this.navigate(MainDestinations.PostReview + companyId)
}

internal fun NavHostController.navigateToNotifications() {
    this.navigate(UserDestination.Notifications)
}

internal fun NavController.navigateBottomMenu(
    navigationRoute: String,
    navGraphId: Int,
) {
    this.navigate(navigationRoute) {
        popUpTo(navGraphId) {
            inclusive = true
        }
        launchSingleTop = true
        restoreState = true
    }
}