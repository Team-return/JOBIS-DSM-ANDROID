package team.retum.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import team.retum.data.remote.url.JobisUrl
import team.retum.data.storage.UserDataStorage
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
            JobisUrl.Code.tech,
            JobisUrl.Code.job,
            JobisUrl.Auth.code,
        )

        if (ignorePath.contains(path)) {
            return chain.proceed(request)
        }

        val accessToken = userDataStorage.fetchAccessToken()

        return chain.proceed(
            request.newBuilder()
                .addHeader("Authorization", accessToken)
                .build()
        )
    }
}