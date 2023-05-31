package team.retum.data.remote.response.recruitment

import com.google.gson.annotations.SerializedName
import team.retum.domain.entity.recruitment.AreasEntity
import team.retum.domain.entity.recruitment.RecruitmentDetailsEntity

data class FetchRecruitmentDetailsResponse(
    @SerializedName("areas") val areas: List<Areas>,
    @SerializedName("preferential_treatment") val preferentialTreatment: String?,
    @SerializedName("required_grade") val requiredGrade: Int?,
    @SerializedName("work_hours") val workHours: Int,
    @SerializedName("required_licenses") val requiredLicenses: List<String>?,
    @SerializedName("hiring_progress") val hiringProgress: List<String>,
    @SerializedName("train_pay") val trainPay: Int,
    @SerializedName("pay") val pay: Int?,
    @SerializedName("benefits") val benefits: String?,
    @SerializedName("military") val military: Boolean,
    @SerializedName("submit_document") val submitDocument: String?,
    @SerializedName("start_date") val startDate: String,
    @SerializedName("end_date") val endDate: String,
    @SerializedName("etc") val etc: String?,


)

data class Areas(
    @SerializedName("recruit_area_id") val recruitAreaId: Long,
    @SerializedName("job") val job: String,
    @SerializedName("tech") val tech: List<String>,
    @SerializedName("hiring") val hiring: Int,
    @SerializedName("major_task") val majorTask: String,
)

fun FetchRecruitmentDetailsResponse.toEntity() = RecruitmentDetailsEntity(
    areas = this.areas.map { it.toEntity() },
    preferentialTreatment = this.preferentialTreatment,
    requiredGrade = this.requiredGrade,
    workHours = this.workHours,
    requiredLicenses = this.requiredLicenses,
    hiringProgress = this.hiringProgress,
    trainPay = this.trainPay,
    pay = this.pay,
    benefits = this.benefits,
    military = this.military,
    submitDocument = this.submitDocument,
    startDate = this.startDate,
    endDate = this.endDate,
    etc = this.etc,
)

private fun Areas.toEntity() = AreasEntity(
    recruitAreaId = this.recruitAreaId,
    job = this.job,
    tech = this.tech,
    hiring = this.hiring,
    majorTask = this.majorTask,
)