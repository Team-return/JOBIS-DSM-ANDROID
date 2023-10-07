package team.retum.data.remote.request.user

import com.google.gson.annotations.SerializedName
import team.retum.domain.enums.AuthCodeType
import team.retum.domain.param.user.SendVerificationCodeParam

data class SendVerificationCodeRequest(
    @SerializedName("email") val email: String,
    @SerializedName("auth_code_type") val authCodeType: AuthCodeType,
)

fun SendVerificationCodeParam.toRequest() = SendVerificationCodeRequest(
    email = this.email,
    authCodeType = this.authCodeType,
)
