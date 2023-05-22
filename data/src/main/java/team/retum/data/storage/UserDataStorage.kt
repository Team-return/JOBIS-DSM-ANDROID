package team.retum.data.storage

import android.content.SharedPreferences

interface UserDataStorage {

    fun fetchAccessToken(): String

    fun fetchRefreshToken(): String

    fun setUserInfo(
        accessToken: String,
        accessTokenExpiresAt: String,
        refreshToken: String,
        refreshTokenExpiresAt: String,
        authority: String,
    )

    fun getPreference(key: String): SharedPreferences

    fun getString(
        key: String
    ): String

    fun putString(
        key: String,
        value: String,
    )

}