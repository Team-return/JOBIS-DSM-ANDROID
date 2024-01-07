package team.retum.jobis_android.feature.auth.signup

import androidx.annotation.StringRes
import team.retum.domain.enums.Gender
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

data class SignUpState(
    val email: String = "",
    val emailError: Boolean = false,
    val verifyCode: String = "",
    val verifyCodeError: Boolean = false,
    val password: String = "",
    val passwordError: Boolean = false,
    val repeatPassword: String = "",
    val repeatPasswordError: Boolean = false,
    val grade: String = "",
    val name: String = "",
    val gender: Gender = Gender.MAN,
    val classRoom: String = "",
    val number: String = "",
    val studentNotFound: Boolean = false,
    val sendVerifyCodeButtonEnabled: Boolean = false,
    val authCodeEnabled: Boolean = false,
    val signUpButtonEnabled: Boolean = false,
) : State

sealed class SignUpSideEffect() : SideEffect {
    sealed class StudentInfo {
        object CheckStudentExistsSuccess : SignUpSideEffect()
        object CheckStudentExistsNotFound : SignUpSideEffect()
    }

    sealed class VerifyEmail {
        object EmailConflict : SignUpSideEffect()
        object VerifyEmailSuccess : SignUpSideEffect()
        object SendAuthCodeSuccess : SignUpSideEffect()
    }

    sealed class SetPassword {
        object SignUpSuccess : SignUpSideEffect()
        object SignUpConflict : SignUpSideEffect()
    }

    class Exception(@StringRes val message: Int) : SignUpSideEffect()
}
