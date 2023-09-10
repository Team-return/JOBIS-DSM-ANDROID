package team.retum.data.util

import retrofit2.HttpException
import team.retum.domain.exception.BadRequestException
import team.retum.domain.exception.ConflictException
import team.retum.domain.exception.ForbiddenException
import team.retum.domain.exception.NotFoundException
import team.retum.domain.exception.OfflineException
import team.retum.domain.exception.OnServerException
import team.retum.domain.exception.TimeoutException
import team.retum.domain.exception.TooLargeRequestException
import team.retum.domain.exception.UnAuthorizationException
import team.retum.domain.exception.UnknownException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class HttpHandler<T> {

    private lateinit var httpRequest: suspend () -> T

    fun httpRequest(httpRequest: suspend () -> T) =
        this.apply { this.httpRequest = httpRequest }

    suspend fun sendRequest(): T =
        try {
            httpRequest()
        } catch (e: HttpException) {
            val code = e.code()
            throw when (code) {
                400 -> BadRequestException()
                401 -> UnAuthorizationException()
                403 -> ForbiddenException()
                404 -> NotFoundException()
                409 -> ConflictException()
                413 -> TooLargeRequestException()
                in 500..599 -> OnServerException()
                else -> UnknownException()
            }
        } catch (e: KotlinNullPointerException) {
            throw e
        } catch (e: SocketTimeoutException) {
            throw TimeoutException()
        } catch (e: UnknownHostException) {
            throw OfflineException()
        } catch (e: Throwable) {
            throw UnknownException()
        }
}