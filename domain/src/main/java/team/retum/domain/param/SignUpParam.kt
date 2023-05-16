package team.retum.domain.param

data class SignUpParam(
    val email: String,
    val password: String,
    val grade: Int,
    val name: String,
    val gender: Sex,
    val classRoom: Int,
    val number: Int,
)

enum class Sex(
    val value: String,
) {
    MAN("남"), WOMAN("여")
}
