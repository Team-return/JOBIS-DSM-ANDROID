package com.jobis.data.storage

import android.content.SharedPreferences

interface UserDataStorage {

    fun putTokens(
        accessToken: String,
        refreshToken: String,
    )

    fun fetchAccessToken(): String
    fun fetchRefreshToken(): String

    fun fetchUserId(): String
    fun fetchPassword(): String

    fun putUserData(
        accountId: String,
        password: String,
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