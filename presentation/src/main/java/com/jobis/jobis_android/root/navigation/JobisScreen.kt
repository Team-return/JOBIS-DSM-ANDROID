package com.jobis.jobis_android.root.navigation

sealed class JobisScreen() {

    object Auth : JobisScreen() {
        const val LOGIN = "Login"
        const val SPLASH = "Splash"
    }

    object Home : JobisScreen(){
        const val HOME = "Home"
    }
}

sealed class JobisRoute(
    val route: String,
) {
    object Auth : JobisRoute("Auth")
    object Home : JobisRoute("Home")
}