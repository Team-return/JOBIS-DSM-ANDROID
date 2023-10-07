package team.retum.domain.param.students

data class ResetPasswordParam(
    val currentPassword: String,
    val newPassword: String,
)
