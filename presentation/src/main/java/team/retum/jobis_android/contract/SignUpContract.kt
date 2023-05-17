package team.retum.jobis_android.contract

import team.retum.domain.param.AuthCodeType
import team.retum.domain.param.Sex
import team.retum.jobis_android.util.mvi.Event
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

data class SignUpState(
    var email: String = "",
    var authCode: String = "",
    var password: String = "",
    var repeatPassword: String = "",
    var grade: Int = 0,
    var name: String = "",
    var gender: Sex = Sex.MAN,
    var `class`: Int = 0,
    var number: Int = 0,
) : State

sealed class SignUpSideEffect() : SideEffect {
    object CheckStudentExistsSuccess : SignUpSideEffect()
    object CheckStudentExistsNotFound : SignUpSideEffect()
    object SendVerificationCodeSuccess : SignUpSideEffect()
    object EmailConflict : SignUpSideEffect()
    object VerifyEmailSuccess : SignUpSideEffect()
    object VerifyEmailUnAuthorized : SignUpSideEffect()
    object VerifyEmailNotFound : SignUpSideEffect()
    object SignUpSuccess : SignUpSideEffect()
    object SignUpConflict : SignUpSideEffect()
    class Exception(val message: String) : SignUpSideEffect()
}

sealed class SignUpEvent : Event {
    class SetSex(val sex: Sex) : SignUpEvent()
    class SetName(val name: String) : SignUpEvent()
    class SetGrade(val grade: Int) : SignUpEvent()
    class SetClass(val `class`: Int) : SignUpEvent()
    class SetNumber(val number: Int) : SignUpEvent()
    class SetEmail(val email: String) : SignUpEvent()
    class SetVerifyCode(val verifyCode: String) : SignUpEvent()
    class SetPassword(val password: String) : SignUpEvent()
    class SetRepeatPassword(val repeatPassword: String) : SignUpEvent()
    object CheckStudentExists: SignUpEvent()
    class SendVerificationCode(
        val email: String,
        val authCodeType: AuthCodeType,
        val userName: String,
    ) : SignUpEvent()
    object VerifyEmail: SignUpEvent()
    object SignUp: SignUpEvent()
}