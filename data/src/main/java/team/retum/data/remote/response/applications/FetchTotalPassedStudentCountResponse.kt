package team.retum.data.remote.response.applications

import com.google.gson.annotations.SerializedName
import team.retum.domain.entity.applications.StudentCountsEntity

data class FetchTotalPassedStudentCountResponse(
    @SerializedName("total_student_count") val totalStudentCount: Long,
    @SerializedName("pass_count") val passCount: Long,
    @SerializedName("approved_count") val approvedCount: Long,
)

internal fun FetchTotalPassedStudentCountResponse.toEntity() = StudentCountsEntity(
    totalStudentCount = this.totalStudentCount,
    passCount = this.passCount,
    approvedCount = this.approvedCount,
)