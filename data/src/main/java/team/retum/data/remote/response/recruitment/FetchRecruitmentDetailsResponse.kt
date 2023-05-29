package team.retum.data.remote.response.recruitment

import com.google.gson.annotations.SerializedName

data class FetchRecruitmentDetailsResponse(
    @SerializedName("areas") val areas: List<Areas>,
    @SerializedName("preferential_treatment") val preferentialTreatment: String?,
    @SerializedName("required_grade") val requiredGrade: Int?,
    @SerializedName("work_hours") val workHours: Int,
    @SerializedName("required_licenses") val requiredLicenses: List<String>?,
    @SerializedName("hiring_progress") val hiringProgress: List<String>,
    @SerializedName("train_pay") val trainPay: Int,
    @SerializedName("pay") val pay: Int?,
    @SerializedName("benefits") val benefits: String,
    @SerializedName("military") val military: Boolean,
    @SerializedName("submit_document") val submitDocument: String?,
    @SerializedName("start_date") val startDate: String,
    @SerializedName("end_date") val endDate: String,
    @SerializedName("etc") val etc: String?,


)

data class Areas(
    @SerializedName("recruit_are_id") val recruitAreId: Long,
    @SerializedName("job") val job: List<String>,
    @SerializedName("tech") val tech: List<String>,
    @SerializedName("hiring") val hiring: Int,
    @SerializedName("major_task") val majorTask: String,
)