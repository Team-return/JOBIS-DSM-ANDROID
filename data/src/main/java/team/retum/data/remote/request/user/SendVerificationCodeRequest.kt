package team.retum.data.remote.request.user

import com.google.gson.annotations.SerializedName
import team.retum.domain.param.user.AuthCodeType
import team.retum.domain.param.user.SendVerificationCodeParam

data class SendVerificationCodeRequest(
    @SerializedName("email") val email: String,
    @SerializedName("type") val type: AuthCodeType,
)

fun SendVerificationCodeParam.toRequest() = SendVerificationCodeRequest(
    email = this.email,
    type = this.type,
)