package team.retum.domain.param

data class VerifyEmailParam(
    val email: String,
    val authCode: String,
)
