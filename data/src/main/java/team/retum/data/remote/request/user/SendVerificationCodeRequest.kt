package team.retum.data.remote.request.user

import com.google.gson.annotations.SerializedName
import team.retum.domain.param.user.AuthCodeType
import team.retum.domain.param.user.SendVerificationCodeParam

data class SendVerificationCodeRequest(
    @SerializedName("email") val email: String,
    @SerializedName("auth_code_type") val authCodeType: AuthCodeType,
    @SerializedName("user_name") val userName: String,
)

fun SendVerificationCodeParam.toRequest() = SendVerificationCodeRequest(
    email = this.email,
    authCodeType = this.authCodeType,
    userName = this.userName,
)