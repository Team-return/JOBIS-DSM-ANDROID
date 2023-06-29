package team.retum.jobis_android.contract

import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

data class ResetPasswordState(
    val email: String = "",
    val authCode: String = "",
    val password: String = "",
    val passwordRepeat: String = "",
    val emailErrorState: Boolean = false,
    val authCodeErrorState: Boolean = false,
    val sendAuthCodeState: Boolean = false,
    val passwordFormatErrorState: Boolean = false,
    val passwordRepeatErrorState: Boolean = false,
    val comparePasswordErrorState: Boolean = false,
) : State

sealed class ResetPasswordSideEffect : SideEffect {
    object SuccessVerification : ResetPasswordSideEffect()
}