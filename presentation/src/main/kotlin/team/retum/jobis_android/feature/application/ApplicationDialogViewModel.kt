package team.retum.jobis_android.feature.application

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
import team.retum.domain.enums.AttachmentDocsType
import team.retum.domain.exception.BadRequestException
import team.retum.domain.exception.ConflictException
import team.retum.domain.exception.NotFoundException
import team.retum.domain.param.application.ApplyCompanyParam
import team.retum.domain.param.application.AttachmentsParam
import team.retum.domain.param.files.PresignedUrlParam
import team.retum.domain.usecase.applications.ApplyCompanyUseCase
import team.retum.domain.usecase.applications.ReApplyCompanyUseCase
import team.retum.domain.usecase.file.CreatePresignedUrlUseCase
import team.retum.domain.usecase.file.UploadPresignedUrlFileUseCase
import team.retum.jobis_android.feature.root.BaseViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
internal class ApplicationDialogViewModel @Inject constructor(
    private val applyCompanyUseCase: ApplyCompanyUseCase,
    private val reApplyCompanyUseCase: ReApplyCompanyUseCase,
    private val createPresignedUrlUseCase: CreatePresignedUrlUseCase,
    private val uploadFileUseCase: UploadPresignedUrlFileUseCase,
) : BaseViewModel<ApplicationState, ApplicationSideEffect>() {

    override val container = container<ApplicationState, ApplicationSideEffect>(ApplicationState())

    internal val urls = mutableStateListOf<String>()
    var files: SnapshotStateList<File> = mutableStateListOf()
        private set

    internal fun setRecruitmentState(
        recruitmentId: Long,
        isReApply: Boolean,
    ) = intent {
        reduce {
            state.copy(
                recruitmentId = recruitmentId,
                isReApply = isReApply,
            )
        }
    }

    internal fun addFile(file: File) {
        files.add(file)
    }

    internal fun removeFile(index: Int) {
        files.removeAt(index)
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
            ).onSuccess { response ->
                response.urls.forEachIndexed { index, url ->
                    uploadFile(
                        presignedUrl = url.presignedUrl,
                        file = files[index],
                    )
                }
                applyCompany(filePaths = response.urls.map { it.filePath })
            }.onFailure {
                when (it) {
                    is BadRequestException -> {
                        postSideEffect(ApplicationSideEffect.InvalidFileExtension)
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

    private fun applyCompany(filePaths: List<String>) = intent {
        viewModelScope.launch(Dispatchers.IO) {
            val result = if (state.isReApply) {
                reApplyCompanyUseCase(
                    applicationId = state.recruitmentId,
                    applyCompanyParam = getApplyCompanyParam(filePaths = filePaths),
                )
            } else {
                applyCompanyUseCase(
                    recruitmentId = state.recruitmentId,
                    applyCompanyParam = getApplyCompanyParam(filePaths = filePaths),
                )
            }
            result.handleApplyEffect()
        }
    }

    private fun getApplyCompanyParam(filePaths: List<String>) = ApplyCompanyParam(
        filePaths.map {
            AttachmentsParam(
                url = it,
                type = AttachmentDocsType.FILE,
            )
        }.toMutableList().apply {
            addAll(
                urls.map {
                    AttachmentsParam(
                        url = it,
                        type = AttachmentDocsType.URL,
                    )
                },
            )
        },
    )

    private fun Result<Unit>.handleApplyEffect() = intent {
        urls.clear()
        files.clear()
        onSuccess {
            postSideEffect(sideEffect = ApplicationSideEffect.SuccessApplyCompany)
        }.onFailure {
            postSideEffect(
                when (it) {
                    is NotFoundException -> ApplicationSideEffect.RecruitmentNotFound
                    is ConflictException -> ApplicationSideEffect.ApplyConflict
                    is KotlinNullPointerException -> ApplicationSideEffect.SuccessApplyCompany
                    else -> ApplicationSideEffect.Exception(
                        message = getStringFromException(it),
                    )
                },
            )
        }
    }
}
