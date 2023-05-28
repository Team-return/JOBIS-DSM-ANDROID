package team.retum.domain.entity.company

data class CompanyDetailsEntity(
    val address1: String,
    val address2: String?,
    val attachments: List<String>,
    val businessNumber: String,
    val companyIntroduce: String,
    val companyName: String,
    val companyProfileUrl: String,
    val email: String,
    val fax: String?,
    val foundedAt: String,
    val manager1: String,
    val manager2: String?,
    val phoneNumber1: String,
    val phoneNumber2: String?,
    val recruitmentId: Int?,
    val representativeName: String,
    val take: Double,
    val workerNumber: Int,
    val zipCode1: String,
)