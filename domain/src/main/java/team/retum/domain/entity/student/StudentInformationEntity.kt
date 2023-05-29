package team.retum.domain.entity.student

data class StudentInformationEntity(
    val studentName: String,
    val studentGcn: String,
    val department: Department,
    val profileImageUrl: String,
)

enum class Department(
    val department: String,
){
    SOFTWARE_DEVELOP("소프트웨어개발과"),
    EMBEDDED_SOFTWARE("임베디드소프트웨어과"),
    INFORMATION_SECURITY("정보보안과"),
    AI_SOFTWARE("인공지능소프트웨어과"),
    DEFAULT(""),
}
