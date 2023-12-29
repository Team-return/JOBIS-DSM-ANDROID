package team.retum.domain.usecase.file

import team.retum.domain.repository.FileRepository
import java.io.File
import javax.inject.Inject

class UploadPresignedUrlFileUseCase @Inject constructor(
    private val fileRepository: FileRepository,
) {
    suspend operator fun invoke(
        presignedUrl: String,
        file: File,
    ) = kotlin.runCatching {
        fileRepository.uploadFile(
            presignedUrl = presignedUrl,
            file = file,
        )
    }
}
