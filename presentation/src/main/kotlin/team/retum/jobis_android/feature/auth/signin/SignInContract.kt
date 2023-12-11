package team.retum.jobis_android.feature.auth.signin

import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

data class SignInState(
    val email: String = "",
    val password: String = "",
    val autoSignIn: Boolean = false,
    val emailError: Boolean = false,
    val passwordError: Boolean = false,
    val signInButtonEnabled: Boolean = false,
) : State

sealed class SignInSideEffect : SideEffect {
    object MoveToMain : SignInSideEffect()
    object UnAuthorization : SignInSideEffect()
    object NotFound : SignInSideEffect()
    class Exception(val message: String) : SignInSideEffect()
}
