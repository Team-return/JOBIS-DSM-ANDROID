package team.retum.jobis_android.contract

import team.retum.domain.param.user.Sex
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
    val sex: Sex = Sex.MAN,
    val `class`: String = "",
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
        object VerifyCodeMismatch : SignUpSideEffect()
        object VerifyEmailNotFound : SignUpSideEffect()
    }

    sealed class SetPassword {
        object SignUpSuccess : SignUpSideEffect()
        object SignUpConflict : SignUpSideEffect()
    }

    class Exception(val message: String) : SignUpSideEffect()
}