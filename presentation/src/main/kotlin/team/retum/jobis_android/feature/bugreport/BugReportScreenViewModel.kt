package team.retum.jobis_android.feature.bugreport

import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.entity.FileType
import team.retum.domain.enums.DevelopmentArea
import team.retum.domain.param.bugreport.BugReportParam
import team.retum.domain.param.files.PresignedUrlParam
import team.retum.domain.usecase.bug.BugReportUseCase
import team.retum.domain.usecase.file.CreatePresignedUrlUseCase
import team.retum.domain.usecase.file.UploadPresignedUrlFileUseCase
import team.retum.jobis_android.feature.root.BaseViewModel
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State
import java.io.File
import javax.inject.Inject

@HiltViewModel
internal class BugReportScreenViewModel @Inject constructor(
    private val bugReportUseCase: BugReportUseCase,
    private val createPresignedUrlUseCase: CreatePresignedUrlUseCase,
    private val uploadFileUseCase: UploadPresignedUrlFileUseCase,
) : BaseViewModel<BugState, BugSideEffect>() {

    override val container = container<BugState, BugSideEffect>(BugState())

    var uris: SnapshotStateList<Uri> = mutableStateListOf()
        private set
    private val files: SnapshotStateList<File> = mutableStateListOf()

    var title by mutableStateOf("")
        private set
    var content by mutableStateOf("")
        private set

    internal fun addFile(
        file: File,
        uri: Uri,
    ) {
        files.add(file)
        uris.add(uri)
    }

    internal fun removeFile(index: Int) {
        files.removeAt(index)
        uris.removeAt(index)
    }

    private suspend fun reportBug(attachmentUrls: List<String>) = intent {
        bugReportUseCase(
            BugReportParam(
                title = title,
                content = content,
                developmentArea = state.selectedPosition,
                attachmentUrls = attachmentUrls,
            ),
        ).onSuccess {
            postSideEffect(BugSideEffect.SuccessReportBug)
        }.onFailure {
            postSideEffect(BugSideEffect.Exception(getStringFromException(it)))
        }
    }

    internal fun createPresignedUrl() {
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
                    uploadFile(
                        presignedUrl = url.presignedUrl,
                        file = files[index],
                    )
                }
                reportBug(it.urls.map { it.filePath })
            }
        }
    }

    private suspend fun uploadFile(
        presignedUrl: String,
        file: File,
    ) {
        uploadFileUseCase(
            presignedUrl = presignedUrl,
            file = file,
        )
    }

    internal fun setTitle(title: String) {
        this.title = title
        intent {
            reduce {
                state.copy(titleError = title.isBlank())
            }
        }
        setReportBugButtonState()
    }

    internal fun setContent(content: String) {
        this.content = content
        intent {
            reduce {
                state.copy(contentError = content.isBlank())
            }
        }
        setReportBugButtonState()
    }

    internal fun setPosition(position: String) = intent {
        reduce {
            state.copy(
                selectedPosition = DevelopmentArea.values().find {
                    it.value == position
                } ?: DevelopmentArea.ALL,
            )
        }
    }

    private fun setReportBugButtonState() = intent {
        reduce {
            with(state) {
                copy(reportBugButtonState = !titleError && !contentError)
            }
        }
    }
}

internal data class BugState(
    val titleError: Boolean = false,
    val contentError: Boolean = false,
    val selectedPosition: DevelopmentArea = DevelopmentArea.ALL,
    val reportBugButtonState: Boolean = false,
) : State

sealed class BugSideEffect : SideEffect {
    object SuccessReportBug : BugSideEffect()
    class Exception(@StringRes val message: Int) : BugSideEffect()
}
