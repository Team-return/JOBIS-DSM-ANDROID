package team.retum.domain.param.user

data class VerifyEmailParam(
    val email: String,
    val authCode: String,
)
