package team.retum.data.remote.request.bugs

import com.google.gson.annotations.SerializedName
import team.retum.domain.param.bugreport.ReportBugParam
import team.retum.domain.param.bugreport.Position

data class ReportBugRequest(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("development_area") val developmentArea: Position,
    @SerializedName("attachment_urls") val attachmentUrls: List<String>,
)

fun ReportBugParam.toRequest() = ReportBugRequest(
    title = this.title,
    content = this.content,
    developmentArea = this.developmentArea,
    attachmentUrls = this.attachmentUrls,
)
