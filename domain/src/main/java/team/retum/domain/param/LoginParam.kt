package team.retum.domain.param

data class LoginParam(
    val accountId: String,
    val password: String,
    val isAutoLogin: Boolean,
)
