package team.retum.domain.entity.recruitment

data class RecruitmentDetailsEntity(
    val areas: List<AreasEntity>,
    val preferentialTreatment: String?,
    val requiredGrade: Int?,
    val workHours: Int,
    val requiredLicenses: List<String>?,
    val hiringProgress: List<HiringProgress>,
    val trainPay: Int,
    val pay: Int?,
    val benefits: String?,
    val military: Boolean,
    val submitDocument: String,
    val startDate: String,
    val endDate: String,
    val etc: String?,

    )

data class AreasEntity(
    val recruitAreaId: Long,
    val job: String,
    val tech: List<String>,
    val hiring: Int,
    val majorTask: String,
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