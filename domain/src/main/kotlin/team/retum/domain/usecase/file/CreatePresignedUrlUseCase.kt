package team.retum.domain.usecase.file

import team.retum.domain.param.files.PresignedUrlParam
import team.retum.domain.repository.FileRepository
import javax.inject.Inject

class CreatePresignedUrlUseCase @Inject constructor(
    private val fileRepository: FileRepository,
) {
    suspend operator fun invoke(presignedUrlParam: PresignedUrlParam) = runCatching {
        fileRepository.createPresignedUrl(presignedUrlParam = presignedUrlParam)
    }
}
