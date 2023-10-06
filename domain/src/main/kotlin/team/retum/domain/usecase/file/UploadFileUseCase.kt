package team.retum.domain.usecase.file

import android.net.Uri
import team.retum.domain.entity.FileType
import team.retum.domain.repository.FileRepository
import java.io.File
import javax.inject.Inject

class UploadFileUseCase @Inject constructor(
    private val fileRepository: FileRepository,
){
    suspend operator fun invoke(
        type: FileType,
        files: List<File>,
    ) = runCatching{
        fileRepository.uploadFile(
            type = type,
            files = files,
        )
    }
}