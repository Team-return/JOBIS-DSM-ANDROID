package team.retum.data.interceptor

import com.jobis.data.BuildConfig
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import org.joda.time.LocalDateTime
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import team.retum.data.remote.api.AuthApi
import team.retum.data.remote.url.JobisUrl
import team.retum.data.storage.UserDataStorage
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    private val userDataStorage: UserDataStorage,
) : Interceptor {

    private val ignorePath by lazy {
        listOf(
            JobisUrl.User.login,
            JobisUrl.Auth.reissue,
            JobisUrl.Student.signup,
            JobisUrl.Auth.code,
            JobisUrl.Student.exists,
            JobisUrl.Student.forgottenPassword,
        )
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val path = request.url.encodedPath

        val accessExpiresAt = userDataStorage.fetchAccessTokenExpiresAt()

        if (accessExpiresAt.isNotBlank() && LocalDateTime.now()
                .isAfter(LocalDateTime.parse(accessExpiresAt)) && !path.isIgnorePath()
        ) {
            tokenReissue()
        }

        if (path.isIgnorePath() || request.url.toString().contains(JobisUrl.imageUrl)) {
            return chain.proceed(request)
        }

        val accessToken = userDataStorage.fetchAccessToken()

        return chain.proceed(
            request.newBuilder().addHeader("Authorization", "Bearer $accessToken").build(),
        )
    }

    private fun String.isIgnorePath(): Boolean {
        return ignorePath.contains(this)
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
