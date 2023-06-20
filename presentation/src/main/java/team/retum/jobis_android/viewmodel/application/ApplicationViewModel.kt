package team.retum.jobis_android.viewmodel.application

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.param.application.ApplyCompanyParam
import team.retum.domain.param.application.AttachmentDocsType
import team.retum.domain.param.application.AttachmentsParam
import team.retum.domain.usecase.applications.ApplyCompanyUseCase
import team.retum.jobis_android.contract.ApplicationSideEffect
import team.retum.jobis_android.contract.ApplicationState
import team.retum.jobis_android.util.mvi.Event
import team.retum.jobis_android.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class ApplicationViewModel @Inject constructor(
    private val applyCompanyUseCase: ApplyCompanyUseCase,
) : BaseViewModel<ApplicationState, ApplicationSideEffect>() {

    override fun sendEvent(event: Event) {}

    override val container = container<ApplicationState, ApplicationSideEffect>(ApplicationState())

    internal fun applyCompany() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            applyCompanyUseCase(
                recruitmentId = state.recruitmentId,
                applyCompanyParam = ApplyCompanyParam(attachments = state.attachments)
            ).onSuccess {
                postSideEffect(sideEffect = ApplicationSideEffect.SuccessApplyCompany)
            }.onFailure {

            }
        }
    }

    internal fun setRecruitmentId(
        recruitmentId: Long,
    ) = intent {
        reduce {
            state.copy(
                recruitmentId = recruitmentId,
            )
        }
    }

    internal fun setAttachments(
        fileUrls: List<String>,
        urls: List<String>,
    ) = intent {

        val attachments = mutableListOf<AttachmentsParam>()

        fileUrls.forEach {
            attachments.add(
                AttachmentsParam(
                    url = it,
                    type = AttachmentDocsType.FILE
                )
            )
        }

        urls.forEach {
            attachments.add(
                AttachmentsParam(
                    url = it,
                    type = AttachmentDocsType.URL,
                )
            )
        }

        reduce {
            state.copy(
                attachments = attachments,
            )
        }
    }
}