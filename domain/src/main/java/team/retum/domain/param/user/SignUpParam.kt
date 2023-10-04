package team.retum.domain.param.user

import team.retum.domain.enums.Gender

data class SignUpParam(
    val email: String,
    val password: String,
    val grade: Long,
    val name: String,
    val gender: Gender,
    val classRoom: Long,
    val number: Long,
)
