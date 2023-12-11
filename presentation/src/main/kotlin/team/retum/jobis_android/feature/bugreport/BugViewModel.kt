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
import team.retum.domain.enums.DevelopmentArea
import team.retum.domain.param.bugreport.ReportBugParam
import team.retum.domain.usecase.bug.ReportBugUseCase
import team.retum.jobis_android.feature.root.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class BugViewModel @Inject constructor(
    private val reportBugUseCase: ReportBugUseCase,
) : BaseViewModel<BugState, BugSideEffect>() {

    override val container = container<BugState, BugSideEffect>(BugState())

    private val fileUrls: MutableList<String> = mutableListOf()
    internal val imageUris: SnapshotStateList<Uri> = mutableStateListOf()

    internal fun reportBug() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            reportBugUseCase(
                ReportBugParam(
                    title = state.title,
                    content = state.content,
                    developmentArea = state.selectedPosition,
                    attachmentUrls = fileUrls,
                ),
            ).onSuccess {
                postSideEffect(BugSideEffect.SuccessReportBug)
            }.onFailure {
                postSideEffect(BugSideEffect.Exception(getStringFromException(it)))
            }
        }
    }

    internal fun setTitle(
        title: String,
    ) = intent {
        reduce {
            state.copy(title = title)
        }
        setTitleError(title.isBlank())
    }

    internal fun setContent(
        content: String,
    ) = intent {
        reduce {
            state.copy(content = content)
        }
        setContentError(content.isBlank())
    }

    internal fun setPosition(
        position: String,
    ) = intent {
        reduce {
            state.copy(
                selectedPosition = DevelopmentArea.values().find { it.value == position }
                    ?: DevelopmentArea.ALL,
            )
        }
    }

    internal fun addUrl(url: String) {
        fileUrls.add(url)
    }

    internal fun removeUrl(index: Int) {
        fileUrls.removeAt(index)
    }

    private fun setReportBugButtonState(enabled: Boolean = true) = intent {
        reduce {
            val title = state.title
            val content = state.content
            state.copy(reportBugButtonState = enabled && title.isNotBlank() && content.isNotBlank())
        }
    }

    private fun setTitleError(titleError: Boolean) = intent {
        reduce {
            state.copy(titleError = titleError)
        }
        setReportBugButtonState(!titleError && !state.contentError)
    }

    private fun setContentError(contentError: Boolean) = intent {
        reduce {
            state.copy(contentError = contentError)
        }
        setReportBugButtonState(!state.titleError && !contentError)
    }
}
