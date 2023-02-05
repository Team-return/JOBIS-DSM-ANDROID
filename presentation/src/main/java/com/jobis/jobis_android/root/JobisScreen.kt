package com.jobis.jobis_android

sealed class JobisScreen(
    val route: String,
) {
    object SPLASH: JobisScreen(route = Route.splash)
    object LOGIN : JobisScreen(route = Route.login)
}

object Route{
    const val splash = "Splash"
    const val login = "Login"
}