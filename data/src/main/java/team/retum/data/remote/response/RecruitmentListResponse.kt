package team.retum.data.remote.response

import com.google.gson.annotations.SerializedName
import team.retum.domain.entity.RecruitmentEntity
import team.retum.domain.entity.RecruitmentListEntity

data class RecruitmentListResponse(
    @SerializedName("recruitments") val recruitments: List<Recruitment>,
)

data class Recruitment(
    @SerializedName("recruit_id") val recruitId: Int,
    @SerializedName("company_name") val companyName: String,
    @SerializedName("company_profile_url") val companyProfileUrl: String,
    @SerializedName("train_pay") val trainPay: Int,
    @SerializedName("military") val military: Boolean,
    @SerializedName("total_hiring") val totalHiring: Int,
    @SerializedName("job_code_list") val jobCodeList: List<String>,
)

fun RecruitmentListResponse.toEntity() = RecruitmentListEntity(
    recruitmentEntities = this.recruitments.map { it.toEntity() }
)

private fun Recruitment.toEntity() = RecruitmentEntity(
    recruitId = this.recruitId,
    companyName = this.companyName,
    companyProfileUrl = this.companyProfileUrl,
    trainPay = this.trainPay,
    military = this.military,
    totalHiring = this.totalHiring,
    jobCodeList = this.jobCodeList,
)