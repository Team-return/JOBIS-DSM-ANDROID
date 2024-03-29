package team.retum.data.remote.response.recruitment

import com.google.gson.annotations.SerializedName
import team.retum.domain.entity.recruitment.RecruitmentEntity
import team.retum.domain.entity.recruitment.RecruitmentsEntity

data class RecruitmentsResponse(
    @SerializedName("recruitments") val recruitments: List<Recruitment>,
)

data class Recruitment(
    @SerializedName("id") val recruitId: Long,
    @SerializedName("company_name") val companyName: String,
    @SerializedName("company_profile_url") val companyProfileUrl: String,
    @SerializedName("train_pay") val trainPay: Long,
    @SerializedName("military_support") val military: Boolean,
    @SerializedName("total_hiring") val totalHiring: Long,
    @SerializedName("hiring_jobs") val hiringJobs: String,
    @SerializedName("bookmarked") val bookmarked: Boolean,
)

fun RecruitmentsResponse.toEntity() = RecruitmentsEntity(
    recruitmentEntities = this.recruitments.map { it.toEntity() },
)

private fun Recruitment.toEntity() = RecruitmentEntity(
    recruitId = this.recruitId,
    companyName = this.companyName,
    companyProfileUrl = this.companyProfileUrl,
    trainPay = this.trainPay,
    military = this.military,
    totalHiring = this.totalHiring,
    hiringJobs = this.hiringJobs,
    bookmarked = this.bookmarked,
)
