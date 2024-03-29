package team.retum.data.remote.response.recruitment

import com.google.gson.annotations.SerializedName
import team.retum.domain.entity.recruitment.AreasEntity
import team.retum.domain.entity.recruitment.RecruitmentDetailsEntity
import team.retum.domain.enums.HiringProgress

data class FetchRecruitmentDetailsResponse(
    @SerializedName("areas") val areas: List<Areas>,
    @SerializedName("benefits") val benefits: String?,
    @SerializedName("company_id") val companyId: Long,
    @SerializedName("company_name") val companyName: String,
    @SerializedName("company_profile_url") val companyProfileUrl: String,
    @SerializedName("end_date") val endDate: String,
    @SerializedName("end_time") val endTime: String,
    @SerializedName("etc") val etc: String?,
    @SerializedName("hiring_progress") val hiringProgress: List<HiringProgress>,
    @SerializedName("military") val military: Boolean,
    @SerializedName("pay") val pay: String?,
    @SerializedName("required_grade") val requiredGrade: Long?,
    @SerializedName("required_licenses") val requiredLicenses: List<String>?,
    @SerializedName("start_date") val startDate: String,
    @SerializedName("start_time") val startTime: String,
    @SerializedName("submit_document") val submitDocument: String,
    @SerializedName("train_pay") val trainPay: Long,
)

data class Areas(
    @SerializedName("hiring") val hiring: Long,
    @SerializedName("id") val id: Long,
    @SerializedName("job") val job: List<String>,
    @SerializedName("major_task") val majorTask: String,
    @SerializedName("tech") val tech: List<String>,
    @SerializedName("preferential_treatment") val preferentialTreatment: String?,
)

fun FetchRecruitmentDetailsResponse.toEntity() = RecruitmentDetailsEntity(
    areas = this.areas.map { it.toEntity() },
    benefits = this.benefits,
    companyId = this.companyId,
    companyName = this.companyName,
    companyProfileUrl = this.companyProfileUrl,
    endDate = this.endDate,
    endTime = this.endTime,
    etc = this.etc,
    hiringProgress = this.hiringProgress,
    military = this.military,
    pay = this.pay,
    requiredGrade = this.requiredGrade,
    requiredLicenses = this.requiredLicenses,
    startDate = this.startDate,
    startTime = this.startTime,
    submitDocument = this.submitDocument,
    trainPay = this.trainPay,
)

private fun Areas.toEntity() = AreasEntity(
    hiring = this.hiring,
    id = this.id,
    job = this.job,
    majorTask = this.majorTask,
    tech = this.tech,
    preferentialTreatment = this.preferentialTreatment,
)
