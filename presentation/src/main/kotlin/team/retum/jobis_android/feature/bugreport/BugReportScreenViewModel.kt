package team.retum.jobis_android.feature.bugreport

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
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
                title = state.title,
                content = state.content,
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

    internal fun setTitle(title: String) = intent {
        reduce {
            state.copy(
                title = title,
                titleError = title.isBlank(),
            )
        }
        setReportBugButtonState()
    }

    internal fun setContent(content: String) = intent {
        reduce {
            state.copy(
                content = content,
                contentError = content.isBlank(),
            )
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
