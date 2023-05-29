package team.retum.domain.entity.recruitment

data class RecruitmentsEntity(
    val recruitmentEntities: List<RecruitmentEntity>,
)

data class RecruitmentEntity(
    val recruitId: Int,
    val companyName: String,
    val companyProfileUrl: String,
    val trainPay: Int,
    val military: Boolean,
    val totalHiring: Int,
    val jobCodeList: String,
    val bookmarked: Boolean,
)
