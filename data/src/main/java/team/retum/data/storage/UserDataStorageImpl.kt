package team.retum.data.storage

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserDataStorageImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : UserDataStorage {

    override fun fetchAccessToken(): String =
        getString(UserPersonalKey.ACCESS_TOKEN)

    override fun fetchRefreshToken(): String =
        getString(UserPersonalKey.REFRESH_TOKEN)

    override fun setUserInfo(
        accessToken: String,
        accessTokenExpiresAt: String,
        refreshToken: String,
        refreshTokenExpiresAt: String,
        authority: String,
    ) {
        putString(UserPersonalKey.ACCESS_TOKEN, accessToken)
        putString(UserPersonalKey.ACCESS_TOKEN_EXPIRES_AT, accessTokenExpiresAt)
        putString(UserPersonalKey.REFRESH_TOKEN, refreshToken)
        putString(UserPersonalKey.REFRESH_TOKEN_EXPIRES_AT, refreshTokenExpiresAt)
    }

    override fun getPreference(key: String): SharedPreferences =
        context.getSharedPreferences(key, MODE_PRIVATE)

    override fun getString(
        key: String,
    ): String =
        getPreference(key).getString(key, "").toString()

    override fun putString(
        key: String,
        value: String,
    ) {
        getPreference(key).edit().putString(key, value).apply()
    }

    private object UserPersonalKey {
        const val ACCESS_TOKEN = "AccessToken"
        const val REFRESH_TOKEN = "RefreshToken"
        const val ACCESS_TOKEN_EXPIRES_AT = "AccessToken_Expires_At"
        const val REFRESH_TOKEN_EXPIRES_AT = "RefreshToken_Expires_At"
    }
}