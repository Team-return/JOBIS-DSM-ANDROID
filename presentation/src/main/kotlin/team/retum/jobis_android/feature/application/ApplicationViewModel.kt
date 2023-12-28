package team.retum.jobis_android.feature.application

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.enums.AttachmentDocsType
import team.retum.domain.exception.ConflictException
import team.retum.domain.exception.NotFoundException
import team.retum.domain.param.application.ApplyCompanyParam
import team.retum.domain.param.application.AttachmentsParam
import team.retum.domain.usecase.applications.ApplyCompanyUseCase
import team.retum.domain.usecase.applications.ReApplyCompanyUseCase
import team.retum.jobis_android.feature.root.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class ApplicationViewModel @Inject constructor(
    private val applyCompanyUseCase: ApplyCompanyUseCase,
    private val reApplyCompanyUseCase: ReApplyCompanyUseCase,
) : BaseViewModel<ApplicationState, ApplicationSideEffect>() {

    override val container = container<ApplicationState, ApplicationSideEffect>(ApplicationState())

    private val attachments = mutableStateListOf<AttachmentsParam>()
    internal val urls = mutableStateListOf<String>()

    internal fun applyCompany(filePaths: List<String>) = intent {
        attachments.run {
            addAll(
                filePaths.map { filePath: String ->
                    AttachmentsParam(
                        url = filePath,
                        type = AttachmentDocsType.FILE,
                    )
                },
            )
            addAll(
                urls.map { url: String ->
                    AttachmentsParam(
                        url = url,
                        type = AttachmentDocsType.URL,
                    )
                },
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            applyCompanyUseCase(
                recruitmentId = state.recruitmentId,
                applyCompanyParam = ApplyCompanyParam(attachments),
            ).handleApplyEffect()
        }
    }

    internal fun reApplyCompany() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            reApplyCompanyUseCase(
                applicationId = state.recruitmentId,
                applyCompanyParam = ApplyCompanyParam(attachments = state.attachments),
            ).handleApplyEffect()
        }
    }

    private fun Result<Unit>.handleApplyEffect() = intent {
        attachments.clear()
        urls.clear()
        onSuccess {
            postSideEffect(sideEffect = ApplicationSideEffect.SuccessApplyCompany)
        }.onFailure {
            when (it) {
                is NotFoundException -> postSideEffect(ApplicationSideEffect.RecruitmentNotFound)
                is ConflictException -> postSideEffect(ApplicationSideEffect.ApplyConflict)
                is KotlinNullPointerException -> postSideEffect(ApplicationSideEffect.SuccessApplyCompany)
                else -> postSideEffect(
                    ApplicationSideEffect.Exception(
                        message = getStringFromException(it),
                    ),
                )
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
}
