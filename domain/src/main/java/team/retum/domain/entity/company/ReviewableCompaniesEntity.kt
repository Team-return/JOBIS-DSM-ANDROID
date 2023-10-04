package team.retum.domain.entity.company

data class ReviewableCompaniesEntity(
    val companies: List<ReviewableCompanyEntity>,
)

data class ReviewableCompanyEntity(
    val id: Long,
    val name: String,
)