package com.jobis.domain.exception

class BadRequestException(): Throwable() // 400
class UnAuthorizationException(): Throwable() // 401
class NotFoundException(): Throwable() // 404
class ConflictException(): Throwable() // 409
class OtherException(): Throwable()


