package team.retum.domain.entity.recruitment

data class RecruitmentDetailsEntity(
    val areas: List<AreasEntity>,
    val benefits: String?,
    val companyId: Long,
    val companyName: String,
    val companyProfileUrl: String,
    val endDate: String,
    val etc: String?,
    val hiringProgress: List<HiringProgress>,
    val military: Boolean,
    val pay: Int?,
    val preferentialTreatment: String?,
    val requiredGrade: Int?,
    val requiredLicenses: List<String>?,
    val startDate: String,
    val submitDocument: String,
    val trainPay: Int,
    val workHours: Int,
)

data class AreasEntity(
    val hiring: Long,
    val id: Long,
    val job: String,
    val majorTask: String,
    val tech: List<String>,
)

enum class HiringProgress(
    val value: String,
){
    CULTURE_INTERVIEW("컬처 면접"),
    DOCUMENT("서류전형"),
    TASK("과제 제출"),
    LIVE_CODING("라이브코딩"),
    TECH_INTERVIEW("기술면접"),
    FINAL_INTERVIEW("최종면접"),
    PERSONALITY("인적성 테스트"),
    AI("AI 면접"),
    CODING_TEST("코딩테스트")
}