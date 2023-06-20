package team.retum.jobis_android.viewmodel.file

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.entity.FileType
import team.retum.domain.usecase.file.UploadFileUseCase
import team.retum.jobis_android.contract.FileSideEffect
import team.retum.jobis_android.contract.FileState
import team.retum.jobis_android.util.mvi.Event
import team.retum.jobis_android.viewmodel.BaseViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
internal class FileViewModel @Inject constructor(
    private val uploadFileUseCase: UploadFileUseCase,
) : BaseViewModel<FileState, FileSideEffect>() {

    override fun sendEvent(event: Event) {}

    override val container = container<FileState, FileSideEffect>(FileState())

    internal fun uploadFile() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            uploadFileUseCase(
                type = state.type,
                files = state.files,
            ).onSuccess {
                postSideEffect(sideEffect = FileSideEffect.SuccessUploadFile(it.urls))
            }.onFailure {

            }
        }
    }

    internal fun setType(
        type: FileType,
    ) = intent {
        reduce {
            state.copy(
                type = type
            )
        }
    }

    internal fun setFiles(
        file: File,
    ) = intent {
        val files = state.files
        files.add(file)

        reduce {
            state.copy(
                files = files,
            )
        }
    }
}

