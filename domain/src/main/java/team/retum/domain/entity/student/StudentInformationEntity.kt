package team.retum.domain.entity.student

import team.retum.domain.enums.Department

data class StudentInformationEntity(
    val studentName: String,
    val studentGcn: String,
    val department: Department,
    val profileImageUrl: String,
)
