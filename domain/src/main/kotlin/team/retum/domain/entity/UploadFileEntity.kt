package team.retum.domain.entity

data class UploadFileEntity(
    val urls: List<String>,
)

enum class FileType {
    LOGO_IMAGE,
    EXTENSION_FILE,
}
