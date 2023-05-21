package team.retum.domain.entity

data class UserApplyCompaniesEntity(
    val name: String,
    val gcn: String,
    val applyCompanies: List<ApplyCompaniesEntity>,
    val totalStudentCount: Int,
    val passCount: Int,
    val approvedCount: Int,
)

data class ApplyCompaniesEntity(
    val companyName: String,
    val status: String,
)
