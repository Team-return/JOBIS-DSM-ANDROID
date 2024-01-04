package team.retum.jobis_android.feature.auth.resetpassword

import androidx.annotation.StringRes
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

data class ResetPasswordState(
    val email: String = "",
    val authCode: String = "",
    val currentPassword: String = "",
    val newPassword: String = "",
    val passwordRepeat: String = "",
    val emailErrorState: Boolean = false,
    val authCodeErrorState: Boolean = false,
    val sendAuthCodeState: Boolean = false,
    val passwordFormatErrorState: Boolean = false,
    val passwordRepeatErrorState: Boolean = false,
    val comparePasswordErrorState: Boolean = false,
    val buttonEnabled: Boolean = false,
) : State

sealed class ResetPasswordSideEffect : SideEffect {
    object SuccessVerification : ResetPasswordSideEffect()
    object SuccessChangePassword : ResetPasswordSideEffect()
    object SuccessResetPassword : ResetPasswordSideEffect()
    object PasswordMismatch : ResetPasswordSideEffect()
    object ClearFocus : ResetPasswordSideEffect()
    class Exception(@StringRes val message: Int) : ResetPasswordSideEffect()
}
