package team.retum.data.remote.request.bugs

import com.google.gson.annotations.SerializedName
import team.retum.domain.param.bugreport.BugReportParam
import team.retum.domain.param.bugreport.Position

data class BugReportRequest(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("development_area") val developmentArea: Position,
    @SerializedName("attachment_urls") val attachmentUrls: List<String>,
)

fun BugReportParam.toRequest() = BugReportRequest(
    title = this.title,
    content = this.content,
    developmentArea = this.developmentArea,
    attachmentUrls = this.attachmentUrls,
)
