package team.retum.domain.entity

data class RecruitmentListEntity(
    val recruitmentEntities: List<RecruitmentEntity>,
)

data class RecruitmentEntity(
    val recruitId: Int,
    val companyName: String,
    val companyProfileUrl: String,
    val trainPay: Int,
    val military: Boolean,
    val totalHiring: Int,
    val jobCodeList: List<String>,
    val bookmarked: Boolean,
)
