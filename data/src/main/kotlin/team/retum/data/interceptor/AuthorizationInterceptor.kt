package team.retum.data.interceptor

import com.jobis.data.BuildConfig
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import team.retum.data.remote.api.AuthApi
import team.retum.data.remote.url.JobisUrl
import team.retum.data.storage.UserDataStorage
import java.time.LocalDateTime
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    private val userDataStorage: UserDataStorage,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val path = request.url.encodedPath

        val ignorePath = arrayListOf(
            JobisUrl.User.login,
            JobisUrl.User.reissue,
            JobisUrl.Student.signup,
            JobisUrl.Auth.code,
            JobisUrl.Student.exists,
        )

        val accessExpiresAt = userDataStorage.fetchAccessTokenExpiresAt()

        if (accessExpiresAt.isNotBlank() && LocalDateTime.now()
                .isAfter(LocalDateTime.parse(accessExpiresAt))
        ) {
            tokenReissue()
        }

        if (ignorePath.contains(path)) {
            return chain.proceed(request)
        }

        val accessToken = userDataStorage.fetchAccessToken()

        return chain.proceed(
            request.newBuilder().addHeader("Authorization", "Bearer $accessToken").build(),
        )
    }

    private fun tokenReissue() {
        val retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val authApi = retrofit.create(AuthApi::class.java)

        runBlocking {
            runCatching {
                authApi.tokenReissue(userDataStorage.fetchRefreshToken())
            }.onSuccess { response ->
                response.apply {
                    userDataStorage.setUserInfo(
                        accessToken = accessToken,
                        accessTokenExpiresAt = accessExpiresAt,
                        refreshToken = refreshToken,
                        refreshTokenExpiresAt = refreshExpiresAt,
                        authority = authority,
                    )
                }
            }
        }
    }
}
