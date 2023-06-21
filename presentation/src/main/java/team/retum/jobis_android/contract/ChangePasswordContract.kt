package team.retum.jobis_android.contract

import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

data class ChangePasswordState(
    val email: String = "",
    val authCode: String = "",
    val password: String = "",
    val emailErrorState: Boolean = false,
): State

sealed class ChangePasswordSideEffect: SideEffect{
    object SuccessVerification: ChangePasswordSideEffect()
}