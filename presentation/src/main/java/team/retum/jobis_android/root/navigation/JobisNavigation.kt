package team.retum.jobis_android.root.navigation

import androidx.navigation.NavHostController

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
    recruitmentId: Long,
) {
    this.navigate(JobisRoute.RecruitmentDetails + recruitmentId)
}

internal fun NavHostController.navigateToBugReport() {
    this.navigate(JobisRoute.MainNavigation.BugReport)
}

internal fun NavHostController.navigateToComparePassword() {
    this.navigate(JobisRoute.ComparePassword)
}

