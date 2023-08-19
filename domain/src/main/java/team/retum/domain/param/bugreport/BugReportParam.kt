package team.retum.domain.param.bugreport

data class BugReportParam(
    val title: String,
    val content: String,
    val developmentArea: Position,
    val attachmentUrls: List<String>,
)

enum class Position{
    ALL,
    SERVER,
    WEB,
    ANDROID,
    IOS,
}