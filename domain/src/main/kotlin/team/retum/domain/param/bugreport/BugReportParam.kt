package team.retum.domain.param.bugreport

import team.retum.domain.enums.DevelopmentArea

data class BugReportParam(
    val title: String,
    val content: String,
    val developmentArea: DevelopmentArea,
    val attachmentUrls: List<String>?,
)
