package team.retum.domain.entity.recruitment

import team.retum.domain.enums.HiringProgress

data class RecruitmentDetailsEntity(
    val areas: List<AreasEntity>,
    val benefits: String?,
    val companyId: Long,
    val companyName: String,
    val companyProfileUrl: String,
    val endDate: String,
    val endTime: String,
    val etc: String?,
    val hiringProgress: List<HiringProgress>,
    val military: Boolean,
    val pay: String?,
    val requiredGrade: Long?,
    val requiredLicenses: List<String>?,
    val startDate: String,
    val startTime: String,
    val submitDocument: String,
    val trainPay: Long,
)

data class AreasEntity(
    val hiring: Long,
    val id: Long,
    val job: List<String>,
    val majorTask: String,
    val tech: List<String>,
    val preferentialTreatment: String?,
)
