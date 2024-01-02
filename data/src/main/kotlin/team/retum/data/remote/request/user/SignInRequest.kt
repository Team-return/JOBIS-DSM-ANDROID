package team.retum.data.remote.request.user

import com.google.gson.annotations.SerializedName
import team.retum.domain.enums.PlatformType

data class SignInRequest(
    @SerializedName("account_id") val accountId: String,
    @SerializedName("password") val password: String,
    @SerializedName("platform_type") val platformType: PlatformType = PlatformType.ANDROID,
)
