package com.jobis.data.storage

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.jobis.data.storage.UserDataStorageImpl.User.ACCOUNT_ID
import com.jobis.data.storage.UserDataStorageImpl.User.PASSWORD
import com.jobis.data.storage.UserDataStorageImpl.UserPersonalKey.ACCESS_TOKEN
import com.jobis.data.storage.UserDataStorageImpl.UserPersonalKey.REFRESH_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserDataStorageImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : UserDataStorage {

    override fun putTokens(
        accessToken: String,
        refreshToken: String,
    ) {
        putString(ACCESS_TOKEN, accessToken)
        putString(REFRESH_TOKEN, refreshToken)
    }

    override fun fetchAccessToken(): String =
        getString(ACCESS_TOKEN)

    override fun fetchRefreshToken(): String =
        getString(REFRESH_TOKEN)

    override fun fetchUserId(): String =
        getPreference(ACCOUNT_ID).getString(ACCOUNT_ID, "").toString()

    override fun fetchPassword(): String =
        getPreference(PASSWORD).getString(PASSWORD, "").toString()

    override fun putUserData(accountId: String, password: String) {
        putString(ACCOUNT_ID, accountId)
        putString(PASSWORD, password)
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
    ){
        getPreference(key).edit().putString(key, value).apply()
    }

    private object UserPersonalKey {
        const val ACCESS_TOKEN = "AccessToken"
        const val REFRESH_TOKEN = "RefreshToken"
    }

    private object User {
        const val ACCOUNT_ID = "AccountId"
        const val PASSWORD = "Password"
    }
}