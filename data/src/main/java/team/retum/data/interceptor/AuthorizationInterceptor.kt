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
            JobisUrl.USER.login,
            JobisUrl.USER.reissue,
            JobisUrl.STUDENT.signup,
            JobisUrl.CODE.tech,
            JobisUrl.CODE.job,
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