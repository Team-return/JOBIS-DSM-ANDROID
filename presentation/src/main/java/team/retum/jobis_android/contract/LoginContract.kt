package team.retum.jobis_android.contract

data class SignInState(
    val accountId: String = "",
    val password: String = "",
    val isAutoLogin: Boolean = false,
    val loginErrorMessage: String? = null,
)

sealed class SignInSideEffect {
    object MoveToMain : SignInSideEffect()
    object UnAuthorization : SignInSideEffect()
    object NotFound : SignInSideEffect()
    object ServerException: SignInSideEffect()
}