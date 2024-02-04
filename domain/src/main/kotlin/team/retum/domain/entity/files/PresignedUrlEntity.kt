package team.retum.domain.entity.files

data class PresignedUrlEntity(
    val urls: List<Url>,
) {
    data class Url(
        val filePath: String,
        val presignedUrl: String,
    )
}
