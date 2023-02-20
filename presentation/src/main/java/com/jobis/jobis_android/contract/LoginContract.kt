package com.jobis.jobis_android.contract

data class LoginState(
    val accountId: String = "",
    val password: String = "",
    val isAutoLogin: Boolean = false,
    val loginErrorMessage: String? = null,
)

sealed class LoginSideEffect {
    object Success : LoginSideEffect()
    object UnAuthorization : LoginSideEffect()
    object NotFound : LoginSideEffect()
    object OnServerError: LoginSideEffect()
}