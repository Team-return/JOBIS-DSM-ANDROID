package team.retum.data.remote.response

import com.google.gson.annotations.SerializedName
import team.retum.domain.entity.Department
import team.retum.domain.entity.StudentInformationEntity

data class FetchStudentInformationResponse(
    @SerializedName("student_name") val studentName: String,
    @SerializedName("student_gcn") val studentGcn: String,
    @SerializedName("department") val department: Department,
    @SerializedName("profile_image_url") val profileImageUrl: String,
)

fun FetchStudentInformationResponse.toEntity() = StudentInformationEntity(
    studentName = this.studentName,
    studentGcn = this.studentGcn,
    department = this.department,
    profileImageUrl = this.profileImageUrl,
)
