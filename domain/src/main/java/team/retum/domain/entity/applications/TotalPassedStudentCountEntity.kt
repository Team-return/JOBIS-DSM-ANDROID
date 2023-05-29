package team.retum.domain.entity.applications

data class TotalPassedStudentCountEntity(
    val totalStudentCount: Int,
    val passCount: Int,
    val approvedCount: Int,
)