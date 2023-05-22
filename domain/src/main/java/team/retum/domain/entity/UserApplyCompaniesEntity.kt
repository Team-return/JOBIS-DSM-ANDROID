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
    val status: Status,
)

enum class Status(
    val value: String,
){
    REQUESTED("승인요청"),
    APPROVED("승인완료"),
    FAILED("탈락"),
    PASS("합격"),
    REJECTED("반려"),
}
