package team.retum.jobis_android.root.navigation

import androidx.navigation.NavHostController

internal fun NavHostController.navigateToMain() {
    this.navigate(JobisRoute.Main) {
        popUpTo(JobisRoute.Main) {
            inclusive = true
        }
    }
}

internal fun NavHostController.navigatePopBackStack() {
    this.popBackStack()
}