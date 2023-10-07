package team.retum.domain.entity.company

data class CompaniesEntity(
    val companies: List<CompanyEntity>,
)

data class CompanyEntity(
    val id: Long,
    val name: String,
    val logoUrl: String,
    val take: Float,
    val hasRecruitment: Boolean,
)
