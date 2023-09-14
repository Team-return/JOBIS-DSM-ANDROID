package team.retum.jobis_android.viewmodel.bugreport

import android.net.Uri
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
import team.retum.jobis_android.contract.bugreport.BugSideEffect
import team.retum.jobis_android.contract.bugreport.BugState
import team.retum.jobis_android.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class BugViewModel @Inject constructor(
    private val reportBugUseCase: ReportBugUseCase,
) : BaseViewModel<BugState, BugSideEffect>() {

    override val container = container<BugState, BugSideEffect>(BugState())

    internal fun reportBug(fileUrls: List<String>) = intent {
        viewModelScope.launch(Dispatchers.IO) {
            reportBugUseCase(
                ReportBugParam(
                    title = state.title,
                    content = state.content,
                    developmentArea = state.selectedPosition,
                    attachmentUrls = fileUrls,
                )
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
        runCatching {
            reduce {
                state.copy(selectedPosition = DevelopmentArea.valueOf(position))
            }
        }.onFailure {
            reduce {
                state.copy(selectedPosition = DevelopmentArea.ALL)
            }
        }
    }

    internal fun setFileUrls(
        fileUrls: List<String>
    ) = intent {
        reduce {
            state.copy(fileUrls = fileUrls)
        }
    }

    internal fun addUri(uri: Uri) = intent {
        reduce {
            val uris = mutableListOf<Uri>()
            uris.run {
                addAll(state.uris)
                add(uri)
            }
            state.copy(uris = uris)
        }
        setReportBugButtonState()
    }

    internal fun removeUri(index: Int) = intent {
        reduce {
            val uris = mutableListOf<Uri>()
            uris.run {
                addAll(state.uris)
                removeAt(index)
            }
            state.copy(uris = uris)
        }
        setReportBugButtonState()
    }

    private fun setReportBugButtonState(enabled: Boolean = true) = intent {
        reduce {
            val title = state.title
            val content = state.content
            val uris = state.uris
            state.copy(reportBugButtonState = enabled && title.isNotBlank() && content.isNotBlank() && uris.isNotEmpty())
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