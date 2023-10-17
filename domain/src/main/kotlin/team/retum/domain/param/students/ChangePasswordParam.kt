package team.retum.domain.param.students

data class ChangePasswordParam(
    val currentPassword: String,
    val newPassword: String,
)
