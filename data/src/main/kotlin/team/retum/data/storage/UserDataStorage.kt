package team.retum.data.storage

import android.content.SharedPreferences
import team.retum.domain.enums.Authority

interface UserDataStorage {

    fun fetchAccessToken(): String
    fun fetchRefreshToken(): String

    fun fetchAccessTokenExpiresAt(): String
    fun fetchRefreshTokenExpiresAt(): String

    fun setUserInfo(
        accessToken: String,
        accessTokenExpiresAt: String,
        refreshToken: String,
        refreshTokenExpiresAt: String,
        authority: Authority,
    )

    fun setAutoSignInOption(
        autoSignInOption: Boolean,
    )

    fun fetchAutoSignInOption(): Boolean

    fun getPreference(key: String): SharedPreferences

    fun getString(
        key: String
    ): String

    fun putString(
        key: String,
        value: String,
    )

    fun clearUserInformation()
}