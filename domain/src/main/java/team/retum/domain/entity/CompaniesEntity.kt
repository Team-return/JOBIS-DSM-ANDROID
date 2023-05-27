package team.retum.domain.entity

data class CompaniesEntity(
    val companies: List<CompanyEntity>
)

data class CompanyEntity(
    val id: Int,
    val name: String,
    val logoUrl: String,
    val take: Float,
)