package team.retum.domain.param.user

data class SendVerificationCodeParam(
    val email: String,
    val authCodeType: AuthCodeType,
    val userName: String,
)

enum class AuthCodeType {
    PASSWORD, SIGN_UP,
}