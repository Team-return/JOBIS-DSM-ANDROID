package team.retum.domain.exception

class BadRequestException(): Throwable() // 400
class UnAuthorizationException(): Throwable() // 401
class ForbiddenException(): Throwable() // 403
class NotFoundException(): Throwable() // 404
class ConflictException(): Throwable() // 409
class OnServerException(): Throwable() // 500..599
class TimeoutException(): Throwable() // Timeout
class UnknownException(): Throwable() // UnknownException


