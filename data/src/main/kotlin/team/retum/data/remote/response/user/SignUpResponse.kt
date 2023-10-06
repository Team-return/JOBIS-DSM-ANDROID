package team.retum.data.remote.response.user

import com.google.gson.annotations.SerializedName
import team.retum.domain.enums.Authority

data class SignUpResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("access_expires_at") val accessExpiresAt: String,
    @SerializedName("refresh_expires_at") val refreshExpiresAt: String,
    @SerializedName("authority") val authority: Authority,
)