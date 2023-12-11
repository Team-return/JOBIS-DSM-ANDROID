package team.retum.jobis_android.feature.common

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.entity.FileType
import team.retum.domain.exception.TooLargeRequestException
import team.retum.domain.usecase.file.UploadFileUseCase
import team.retum.jobis_android.feature.root.BaseViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
internal class FileViewModel @Inject constructor(
    private val uploadFileUseCase: UploadFileUseCase,
) : BaseViewModel<FileState, FileSideEffect>() {

    override val container = container<FileState, FileSideEffect>(FileState())

    private val files = container.stateFlow.value.files

    internal fun uploadFile() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            uploadFileUseCase(
                type = state.type,
                files = state.files,
            ).onSuccess {
                postSideEffect(sideEffect = FileSideEffect.SuccessUploadFile(it.urls))
            }.onFailure {
                if (it is TooLargeRequestException) {
                    postSideEffect(FileSideEffect.FileLargeException)
                }
            }
        }
    }

    internal fun setType(
        type: FileType,
    ) = intent {
        reduce {
            state.copy(type = type)
        }
    }

    internal fun addFile(
        file: File,
    ) = intent {
        files.add(file)
        reduce {
            state.copy(files = files)
        }
    }

    internal fun removeFile(
        index: Int,
    ) = intent {
        files.removeAt(index)
        reduce {
            state.copy(
                files = files,
            )
        }
    }
}
