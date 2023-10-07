package team.retum.data.remote.request.user

import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("account_id") val accountId: String,
    @SerializedName("password") val password: String,
)
