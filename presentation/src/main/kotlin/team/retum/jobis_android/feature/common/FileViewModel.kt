package team.retum.jobis_android.feature.common

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.entity.FileType
import team.retum.domain.exception.BadRequestException
import team.retum.domain.param.files.PresignedUrlParam
import team.retum.domain.usecase.file.CreatePresignedUrlUseCase
import team.retum.domain.usecase.file.UploadPresignedUrlFileUseCase
import team.retum.jobis_android.feature.root.BaseViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
internal class FileViewModel @Inject constructor(
    private val createPresignedUrlUseCase: CreatePresignedUrlUseCase,
    private val uploadFileUseCase: UploadPresignedUrlFileUseCase,
) : BaseViewModel<FileState, FileSideEffect>() {

    override val container = container<FileState, FileSideEffect>(FileState())

    internal val files = mutableStateListOf<File>()
    internal val filePaths = mutableListOf<String>()

    internal fun resetFiles() {
        files.clear()
        filePaths.clear()
    }

    internal fun createPresignedUrl() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            createPresignedUrlUseCase(
                presignedUrlParam = PresignedUrlParam(
                    files = files.map {
                        PresignedUrlParam.File(
                            type = FileType.EXTENSION_FILE,
                            fileName = it.name,
                        )
                    },
                ),
            ).onSuccess {
                it.urls.forEachIndexed { index, url ->
                    filePaths.add(url.filePath)
                    uploadFile(
                        presignedUrl = url.presignedUrl,
                        file = files[index],
                    )
                }
                postSideEffect(FileSideEffect.Success)
            }.onFailure {
                when (it) {
                    is BadRequestException -> {
                        postSideEffect(FileSideEffect.InvalidFileExtension)
                    }
                }
            }
        }
    }

    private fun uploadFile(
        presignedUrl: String,
        file: File,
    ) = intent {
        viewModelScope.launch(Dispatchers.IO) {
            uploadFileUseCase(
                presignedUrl = presignedUrl,
                file = file,
            )
        }
    }

    internal fun setType(type: FileType) = intent {
        reduce {
            state.copy(type = type)
        }
    }

    internal fun addFile(file: File) {
        files.add(file)
    }

    internal fun removeFile(index: Int) {
        files.removeAt(index)
    }
}
