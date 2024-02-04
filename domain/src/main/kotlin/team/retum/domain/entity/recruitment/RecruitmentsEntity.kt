package team.retum.domain.entity.recruitment

data class RecruitmentsEntity(
    val recruitmentEntities: List<RecruitmentEntity>,
)

data class RecruitmentEntity(
    val recruitId: Long,
    val companyName: String,
    val companyProfileUrl: String,
    val trainPay: Long,
    val military: Boolean,
    val totalHiring: Long,
    val hiringJobs: String,
    val bookmarked: Boolean,
)
