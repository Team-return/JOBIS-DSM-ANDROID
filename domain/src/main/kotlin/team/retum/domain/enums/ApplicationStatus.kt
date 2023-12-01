package team.retum.domain.enums

enum class ApplicationStatus(
    val status: String,
) {
    REQUESTED("승인요청"),
    APPROVED("승인됨"),
    FAILED("탈락"),
    PASS("합격"),
    REJECTED("반려"),
    SEND("전송됨"),
}
