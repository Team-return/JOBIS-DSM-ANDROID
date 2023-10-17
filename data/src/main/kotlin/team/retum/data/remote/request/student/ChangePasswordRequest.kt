package team.retum.data.remote.request.student

import com.google.gson.annotations.SerializedName
import team.retum.domain.param.students.ChangePasswordParam

data class ChangePasswordRequest(
    @SerializedName("current_password") val currentPassword: String,
    @SerializedName("new_password") val newPassword: String,
)

fun ChangePasswordParam.toRequest() = ChangePasswordRequest(
    currentPassword = this.currentPassword,
    newPassword = this.newPassword,
)
