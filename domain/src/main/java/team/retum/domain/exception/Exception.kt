package team.retum.domain.exception

class BadRequestException(): Throwable() // 400
class UnAuthorizationException(): Throwable() // 401
class NotFoundException(): Throwable() // 404
class ConflictException(): Throwable() // 409
class OnServerException(): Throwable() // 500..599
class OtherException(): Throwable() // Unknown
class TimeoutException(): Throwable() // Timeout


