package team.retum.domain.param.user

data class SendVerificationCodeParam(
    val email: String,
    val type: AuthCodeType,
)

enum class AuthCodeType {
    PASSWORD, SIGN_UP,
}
