package team.retum.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserApplyCompaniesResponse(
    @SerializedName("student_name") val name: String,
    @SerializedName("student_gcn") val gcn: String,
    @SerializedName("apply_companies") val applyCompanies: List<ApplyCompanies>,
    @SerializedName("total_student_count") val totalStudentCount: Int,
    @SerializedName("pass_count") val passCount: Int,
    @SerializedName("approved_count") val approvedCount: Int,
){
    data class ApplyCompanies(
        @SerializedName("company_name") val companyName: String,
        @SerializedName("status") val status: String,
    )
}