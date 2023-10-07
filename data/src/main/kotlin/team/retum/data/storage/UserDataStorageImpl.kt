package team.retum.data.storage

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import team.retum.domain.enums.Authority
import javax.inject.Inject

class UserDataStorageImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : UserDataStorage {

    override fun fetchAccessToken(): String {
        return getString(UserPersonalKey.ACCESS_TOKEN)
    }

    override fun fetchRefreshToken(): String {
        return getString(UserPersonalKey.REFRESH_TOKEN)
    }

    override fun fetchAccessTokenExpiresAt(): String {
        return getString(UserPersonalKey.ACCESS_TOKEN_EXPIRES_AT)
    }

    override fun fetchRefreshTokenExpiresAt(): String {
        return getString(UserPersonalKey.REFRESH_TOKEN_EXPIRES_AT)
    }

    override fun setUserInfo(
        accessToken: String,
        accessTokenExpiresAt: String,
        refreshToken: String,
        refreshTokenExpiresAt: String,
        authority: Authority,
    ) {
        putString(UserPersonalKey.ACCESS_TOKEN, accessToken)
        putString(UserPersonalKey.ACCESS_TOKEN_EXPIRES_AT, accessTokenExpiresAt)
        putString(UserPersonalKey.REFRESH_TOKEN, refreshToken)
        putString(UserPersonalKey.REFRESH_TOKEN_EXPIRES_AT, refreshTokenExpiresAt)
    }

    override fun setAutoSignInOption(
        autoSignInOption: Boolean,
    ) {
        getPreference(UserPersonalKey.AUTO_SIGN_IN).edit()
            .putBoolean(UserPersonalKey.AUTO_SIGN_IN, autoSignInOption).apply()
    }

    override fun fetchAutoSignInOption(): Boolean {
        return getPreference(UserPersonalKey.AUTO_SIGN_IN).getBoolean(
            UserPersonalKey.AUTO_SIGN_IN,
            false,
        )
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

    override fun clearUserInformation() {
        run {
            getPreference(UserPersonalKey.ACCESS_TOKEN)
            getPreference(UserPersonalKey.REFRESH_TOKEN)
            getPreference(UserPersonalKey.ACCESS_TOKEN_EXPIRES_AT)
            getPreference(UserPersonalKey.REFRESH_TOKEN_EXPIRES_AT)
            getPreference(UserPersonalKey.AUTO_SIGN_IN)
        }.edit().clear().apply()
    }

    private object UserPersonalKey {
        const val ACCESS_TOKEN = "accessToken"
        const val REFRESH_TOKEN = "refreshKey"
        const val ACCESS_TOKEN_EXPIRES_AT = "accessTokenExpiresAt"
        const val REFRESH_TOKEN_EXPIRES_AT = "refreshTokenExpiresAt"
        const val AUTO_SIGN_IN = "autoSignIn"
    }
}
