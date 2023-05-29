package team.retum.domain.entity.recruitment

data class RecruitmentDetailsEntity(
    val areas: List<AreasEntity>,
    val preferentialTreatment: String?,
    val requiredGrade: Int?,
    val workHours: Int,
    val requiredLicenses: List<String>?,
    val hiringProgress: List<String>,
    val trainPay: Int,
    val pay: Int?,
    val benefits: String,
    val military: Boolean,
    val submitDocument: String?,
    val startDate: String,
    val endDate: String,
    val etc: String?,

)

data class AreasEntity(
    val recruitAreaId: Long,
    val job: List<String>,
    val tech: List<String>,
    val hiring: Int,
    val majorTask: String,
)