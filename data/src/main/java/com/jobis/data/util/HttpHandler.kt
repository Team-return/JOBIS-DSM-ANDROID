package com.jobis.data.util

import com.jobis.domain.exception.*
import retrofit2.HttpException

class HttpHandler<T> {

    private lateinit var httpRequest: suspend () -> T
    private val onBadRequest: (message: String) -> Throwable = { BadRequestException() }
    private val onNotFound: (message: String) -> Throwable = { NotFoundException() }
    private val onConflict: (message: String) -> Throwable = { ConflictException() }
    private val onServerError: (message: String) -> Throwable = {OnServerException()}
    private val onOtherException: (code: Int, message: String) -> Throwable =
        { _, _ -> OtherException() }

    fun httpRequest(httpRequest: suspend () -> T) =
        this.apply { this.httpRequest = httpRequest }

    suspend fun sendRequest(): T =
        try {
            httpRequest()
        } catch (e: HttpException) {
            val code = e.code()
            val message = e.message()
            throw when (code) {
                400 -> onBadRequest(message)
                404 -> onNotFound(message)
                409 -> onConflict(message)
                in 500..599 -> onServerError(message)
                else -> onOtherException(code, message)
            }
        }
}