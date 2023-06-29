package team.retum.data.remote.request.students

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest(
    @SerializedName("current_password") val currentPassword: String,
    @SerializedName("new_password") val newPassword: String,
)
