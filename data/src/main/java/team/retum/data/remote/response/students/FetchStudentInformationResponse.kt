package team.retum.data.remote.response.students

import com.google.gson.annotations.SerializedName
import team.retum.domain.entity.student.StudentInformationEntity
import team.retum.domain.enums.Department

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
