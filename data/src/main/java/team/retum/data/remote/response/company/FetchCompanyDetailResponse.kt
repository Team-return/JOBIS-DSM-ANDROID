package team.retum.data.remote.response.company

import com.google.gson.annotations.SerializedName
import team.retum.domain.entity.company.CompanyDetailsEntity

data class FetchCompanyDetailResponse(
    @SerializedName("address1") val address1: String,
    @SerializedName("address2") val address2: String?,
    @SerializedName("attachments") val attachments: List<String>,
    @SerializedName("business_number") val businessNumber: String,
    @SerializedName("company_introduce") val companyIntroduce: String,
    @SerializedName("company_name") val companyName: String,
    @SerializedName("company_profile_url") val companyProfileUrl: String,
    @SerializedName("email") val email: String,
    @SerializedName("fax") val fax: String?,
    @SerializedName("founded_at") val foundedAt: String,
    @SerializedName("manager1") val manager1: String,
    @SerializedName("manager2") val manager2: String?,
    @SerializedName("phone_number1") val phoneNumber1: String,
    @SerializedName("phone_number2") val phoneNumber2: String?,
    @SerializedName("recruitment_id") val recruitmentId: Int?,
    @SerializedName("representative_name") val representativeName: String,
    @SerializedName("take") val take: Double,
    @SerializedName("worker_number") val workerNumber: Int,
    @SerializedName("zip_code1") val zipCode1: String,
    @SerializedName("zip_code2") val zipCode2: String,
)

fun FetchCompanyDetailResponse.toEntity() = CompanyDetailsEntity(
    address1 = this.address1,
    address2 = this.address2,
    attachments = this.attachments,
    businessNumber = this.businessNumber,
    companyIntroduce = this.companyIntroduce,
    companyName = this.companyName,
    companyProfileUrl = this.companyProfileUrl,
    email = this.email,
    fax = this.fax,
    foundedAt = this.foundedAt,
    manager1 = this.manager1,
    manager2 = this.manager2,
    phoneNumber1 = this.phoneNumber1,
    phoneNumber2 = this.phoneNumber2,
    recruitmentId = this.recruitmentId,
    representativeName = this.representativeName,
    take = this.take,
    workerNumber = this.workerNumber,
    zipCode1 = this.zipCode1
)