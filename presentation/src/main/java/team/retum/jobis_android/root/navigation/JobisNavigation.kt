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

