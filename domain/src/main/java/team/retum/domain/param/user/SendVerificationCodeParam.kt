package team.retum.domain.param.user

data class SendVerificationCodeParam(
    val email: String,
    val authCodeType: AuthCodeType,
)

enum class AuthCodeType {
    PASSWORD, SIGN_UP,
}
