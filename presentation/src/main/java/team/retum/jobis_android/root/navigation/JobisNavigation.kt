package team.retum.jobis_android.root.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument

internal fun NavHostController.navigateToMain() {
    this.navigate(NavigationRoute.Main) {
        popUpTo(NavigationRoute.Main) {
            inclusive = true
        }
    }
}

internal fun NavHostController.navigateToMainWithPopUpSignIn() {
    this.navigate(NavigationRoute.Main) {
        popUpTo(NavigationRoute.SignIn)
    }
}

internal fun NavHostController.navigateToSignInPopUpWithMain() {
    this.navigate(NavigationRoute.SignIn) {
        popUpTo(NavigationRoute.Main) {
            inclusive = true
        }
    }
}

internal fun NavHostController.navigatePopBackStack() {
    this.popBackStack()
}

internal fun NavHostController.navigateToResetPasswordVerifyEmail() {
    this.navigate(NavigationRoute.ResetPasswordVerifyEmail)
}

internal fun NavHostController.navigateToSignUp() {
    this.navigate(NavigationRoute.SignUp)
}

internal fun NavHostController.navigateToMyPage() {
    this.navigate(NavigationRoute.Navigation.MyPage)
}

internal fun NavHostController.navigateToRecruitments() {
    this.navigate(NavigationRoute.Recruitments)
}

internal fun NavHostController.navigateToCompanies() {
    this.navigate(NavigationRoute.Companies)
}

internal fun NavHostController.navigateToRecruitmentDetails(
    recruitmentId: Long?,
) {
    this.navigate(NavigationRoute.RecruitmentDetails + recruitmentId)
}

internal fun NavHostController.navigateToBugReport() {
    this.navigate(NavigationRoute.MainNavigation.BugReport)
}

internal fun NavHostController.navigateToComparePassword() {
    this.navigate(NavigationRoute.ComparePassword)
}

internal fun NavHostController.navigateToBookmarkRecruitments() {
    this.navigate(NavigationRoute.Navigation.BookmarkRecruitments)
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

internal fun NavHostController.navigateToCompanyDetails(
    companyId: Long,
    hasRecruitment: Boolean,
) {
    this.navigate("${NavigationRoute.CompanyDetails}/${companyId}/${hasRecruitment}")
}

internal fun NavHostController.navigateToReviewDetails(
    reviewId: String,
) {
    this.navigate("${NavigationRoute.MainNavigation.ReviewDetails}${reviewId}")
}

internal fun NavHostController.navigateToResetPassword() {
    this.navigate(NavigationRoute.ResetPassword)
}

internal fun <T> getArgument(
    name: String,
    type: NavType<T>,
): NamedNavArgument {
    return navArgument(name) {
        this.type = type
    }
}