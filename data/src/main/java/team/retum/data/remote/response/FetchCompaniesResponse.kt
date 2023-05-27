package team.retum.data.remote.response

import com.google.gson.annotations.SerializedName

data class FetchCompaniesResponse(
    @SerializedName("companies") val companies: List<Company>,
)

data class Company(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("logo_url") val logoUrl: String,
    @SerializedName("take") val take: Float,
)
