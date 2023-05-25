package team.retum.data.remote.response

import com.google.gson.annotations.SerializedName
import team.retum.domain.entity.TotalPassedStudentCountEntity

data class FetchTotalPassedStudentCountResponse(
    @SerializedName("total_student_count") val totalStudentCount: Int,
    @SerializedName("pass_count") val passCount: Int,
    @SerializedName("approved_count") val approvedCount: Int,
)

internal fun FetchTotalPassedStudentCountResponse.toEntity() = TotalPassedStudentCountEntity(
    totalStudentCount = this.totalStudentCount,
    passCount = this.passCount,
    approvedCount = this.approvedCount,
)