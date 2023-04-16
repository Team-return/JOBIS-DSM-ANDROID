package team.retum.jobis_android.contract

import team.retum.jobis_android.util.mvi.Event
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

data class SignInState(
    val accountId: String = "",
    val password: String = "",
    val isAutoLogin: Boolean = false,
    val loginErrorMessage: String? = null,
) : State

sealed class SignInSideEffect : SideEffect {
    object MoveToMain : SignInSideEffect()
    object UnAuthorization : SignInSideEffect()
    object NotFound : SignInSideEffect()
    class Exception(
        val message: String,
    ): SignInSideEffect()
}

sealed class SignInEvent : Event {
    data class SetId(val id: String) : SignInEvent()
    data class SetPassword(val password: String) : SignInEvent()
}