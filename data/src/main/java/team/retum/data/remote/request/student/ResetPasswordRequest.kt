package team.retum.data.remote.request.student

import com.google.gson.annotations.SerializedName
import team.retum.domain.param.students.ResetPasswordParam

data class ResetPasswordRequest(
    @SerializedName("current_password") val currentPassword: String,
    @SerializedName("new_password") val newPassword: String,
)

fun ResetPasswordParam.toRequest() = ResetPasswordRequest(
    currentPassword = this.currentPassword,
    newPassword = this.newPassword,
)
