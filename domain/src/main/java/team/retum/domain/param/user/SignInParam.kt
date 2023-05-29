package team.retum.domain.param.user

data class SignInParam(
    val accountId: String,
    val password: String,
    val isAutoLogin: Boolean,
)
