package team.retum.data.remote.request.bugs

import com.google.gson.annotations.SerializedName
import team.retum.domain.entity.student.Department

data class BugReportRequest(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("development_area") val developmentArea: Position,
    @SerializedName("attachment_urls") val attachmentUrls: List<String>,
)

enum class Position{
    ALL,
    SERVER,
    WEB,
    ANDROID,
    IOS,
}
