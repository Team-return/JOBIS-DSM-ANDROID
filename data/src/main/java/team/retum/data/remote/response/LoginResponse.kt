package team.retum.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("access_expires_at") val expiresAt: String,
    @SerializedName("authority") val authority: String,
)
