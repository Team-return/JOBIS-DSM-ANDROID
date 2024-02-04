package team.retum.data.remote.request.student

import com.google.gson.annotations.SerializedName
import team.retum.domain.param.students.ResetPasswordParam

data class ResetPasswordRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
)

fun ResetPasswordParam.toRequest() = ResetPasswordRequest(
    email = this.email,
    password = this.password,
)
