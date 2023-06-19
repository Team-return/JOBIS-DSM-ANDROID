package team.retum.jobis_android.viewmodel.file

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
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
): BaseViewModel<FileState, FileSideEffect>() {

    override fun sendEvent(event: Event) {}

    override val container = container<FileState, FileSideEffect>(FileState())

    private val files: MutableList<File> = mutableListOf()

    internal fun uploadFile() = intent{
        setFiles(files = files)
        viewModelScope.launch(Dispatchers.IO){
            uploadFileUseCase(
                type = state.type,
                files = state.files,
            ).onSuccess {
                Log.d("TEST", it.toString())
            }.onFailure {

            }
        }
    }

    internal fun setType(
        type: FileType,
    ) = intent{
        reduce{
            state.copy(
                type = type
            )
        }
    }

    internal fun setFiles(
        files: List<File>,
    ) = intent{
        reduce{
            state.copy(
                files = files,
            )
        }
    }

    internal fun addFile(
        file: File,
    ){
        files.add(file)
    }
}

