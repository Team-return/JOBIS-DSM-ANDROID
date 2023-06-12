package team.retum.domain.entity.applications

data class AppliedCompanyHistoriesEntity(
    val applications: List<AppliedCompanyEntity>,
)

data class AppliedCompanyEntity(
    val applicationId: Int,
    val company: String,
    val attachmentUrlList: List<String>,
    val applicationStatus: Status,
)

enum class Status(
    val status: String,
){
    REQUESTED("승인요청"),
    APPROVED("승인됨"),
    FAILED("탈락"),
    PASS("합격"),
    REJECTED("반려"),
}