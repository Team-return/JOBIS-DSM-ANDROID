package team.retum.jobis_android.contract

data class LoginState(
    val accountId: String = "",
    val password: String = "",
    val isAutoLogin: Boolean = false,
    val loginErrorMessage: String? = null,
)

sealed class LoginSideEffect {
    object MoveToMain : LoginSideEffect()
    object MoveToLogin : LoginSideEffect()
    object UnAuthorization : LoginSideEffect()
    object BadRequest : LoginSideEffect()
    object NotFound : LoginSideEffect()
    object OnServerError : LoginSideEffect()
}