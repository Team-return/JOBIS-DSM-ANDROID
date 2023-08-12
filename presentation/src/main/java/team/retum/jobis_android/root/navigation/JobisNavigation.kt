package team.retum.jobis_android.root.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument

internal fun NavHostController.navigateToMain() {
    this.navigate(JobisRoute.Main) {
        popUpTo(JobisRoute.Main) {
            inclusive = true
        }
    }
}

internal fun NavHostController.navigateToMainWithPopUpSignIn() {
    this.navigate(JobisRoute.Main) {
        popUpTo(JobisRoute.SignIn)
    }
}

internal fun NavHostController.navigateToSignInPopUpWithMain() {
    this.navigate(JobisRoute.SignIn) {
        popUpTo(JobisRoute.Main) {
            inclusive = true
        }
    }
}

internal fun NavHostController.navigatePopBackStack() {
    this.popBackStack()
}

internal fun NavHostController.navigateToResetPasswordVerifyEmail() {
    this.navigate(JobisRoute.ResetPasswordVerifyEmail)
}

internal fun NavHostController.navigateToSignUp() {
    this.navigate(JobisRoute.SignUp)
}

internal fun NavHostController.navigateToMyPage() {
    this.navigate(JobisRoute.Navigation.MyPage)
}

internal fun NavHostController.navigateToRecruitments() {
    this.navigate(JobisRoute.Recruitments)
}

internal fun NavHostController.navigateToCompanies() {
    this.navigate(JobisRoute.Companies)
}

internal fun NavHostController.navigateToRecruitmentDetails(
    recruitmentId: Long?,
) {
    this.navigate(JobisRoute.RecruitmentDetails + recruitmentId)
}

internal fun NavHostController.navigateToBugReport() {
    this.navigate(JobisRoute.MainNavigation.BugReport)
}

internal fun NavHostController.navigateToComparePassword() {
    this.navigate(JobisRoute.ComparePassword)
}

internal fun NavHostController.navigateToBookmarkRecruitments() {
    this.navigate(JobisRoute.Navigation.BookmarkRecruitments)
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
    this.navigate("${JobisRoute.CompanyDetails}/${companyId}/${hasRecruitment}")
}

internal fun NavHostController.navigateToReviewDetails(
    reviewId: String,
) {
    this.navigate("${JobisRoute.MainNavigation.ReviewDetails}${reviewId}")
}

internal fun NavHostController.navigateToResetPassword() {
    this.navigate(JobisRoute.ResetPassword)
}

internal fun <T> getArgument(
    name: String,
    type: NavType<T>,
): NamedNavArgument {
    return navArgument(name) {
        this.type = type
    }
}