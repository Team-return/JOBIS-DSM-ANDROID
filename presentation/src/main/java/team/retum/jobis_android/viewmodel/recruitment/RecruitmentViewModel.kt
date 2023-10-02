package team.retum.jobis_android.viewmodel.recruitment

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
import team.retum.data.remote.url.JobisUrl
import team.retum.domain.entity.recruitment.RecruitmentDetailsEntity
import team.retum.domain.entity.recruitment.RecruitmentEntity
import team.retum.domain.param.recruitment.FetchRecruitmentListParam
import team.retum.domain.usecase.recruitment.FetchRecruitmentCountUseCase
import team.retum.domain.usecase.recruitment.FetchRecruitmentDetailsUseCase
import team.retum.domain.usecase.recruitment.FetchRecruitmentListUseCase
import team.retum.jobis_android.contract.recruitment.RecruitmentSideEffect
import team.retum.jobis_android.contract.recruitment.RecruitmentState
import team.retum.jobis_android.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class RecruitmentViewModel @Inject constructor(
    private val fetchRecruitmentListUseCase: FetchRecruitmentListUseCase,
    private val fetchRecruitmentDetailsUseCase: FetchRecruitmentDetailsUseCase,
    private val fetchRecruitmentCountUseCase: FetchRecruitmentCountUseCase,
) : BaseViewModel<RecruitmentState, RecruitmentSideEffect>() {

    override val container = container<RecruitmentState, RecruitmentSideEffect>(RecruitmentState())

    init {
        fetchRecruitments()
        fetchRecruitmentCount()
    }

    private val _recruitments: SnapshotStateList<RecruitmentUiModel> = mutableStateListOf()

    internal fun fetchRecruitments() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            fetchRecruitmentListUseCase(
                fetchRecruitmentListParam = FetchRecruitmentListParam(
                    page = state.page,
                    jobCode = state.jobCode,
                    techCode = state.techCode,
                    name = state.name,
                )
            ).onSuccess { it ->
                setRecruitments(it.recruitmentEntities.map { it.toModel() })
            }.onFailure { throwable ->
                postSideEffect(
                    sideEffect = RecruitmentSideEffect.Exception(
                        message = getStringFromException(
                            throwable = throwable,
                        )
                    )
                )
            }
        }
    }


    private fun fetchRecruitmentDetails() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            fetchRecruitmentDetailsUseCase(
                recruitmentId = state.recruitmentId,
            ).onSuccess {
                setRecruitmentDetails(
                    recruitmentDetails = it,
                )
            }.onFailure { throwable ->
                postSideEffect(
                    sideEffect = RecruitmentSideEffect.Exception(
                        message = getStringFromException(
                            throwable = throwable,
                        )
                    )
                )
            }
        }
    }

    internal fun fetchRecruitmentCount() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            fetchRecruitmentCountUseCase(
                fetchRecruitmentListParam = FetchRecruitmentListParam(
                    page = state.page,
                    name = state.name,
                    jobCode = state.jobCode,
                    techCode = state.techCode,
                )
            ).onSuccess {
                setRecruitmentCount(it.totalPageCount)
            }
        }
    }

    private fun setRecruitmentDetails(
        recruitmentDetails: RecruitmentDetailsEntity,
    ) = intent {
        reduce {
            with(recruitmentDetails) {
                state.copy(
                    details = RecruitmentDetailsEntity(
                        areas = areas,
                        benefits = benefits,
                        companyId = companyId,
                        companyName = companyName,
                        companyProfileUrl = JobisUrl.imageUrl + companyProfileUrl,
                        endDate = endDate,
                        etc = etc,
                        hiringProgress = hiringProgress,
                        military = military,
                        pay = pay,
                        preferentialTreatment = preferentialTreatment,
                        requiredGrade = requiredGrade,
                        requiredLicenses = requiredLicenses,
                        startDate = startDate,
                        submitDocument = submitDocument,
                        trainPay = trainPay,
                        workHours = workHours,
                    )
                )
            }
        }
    }

    private fun setRecruitmentCount(
        recruitmentCount: Long,
    ) = intent {
        reduce {
            state.copy(recruitmentCount = recruitmentCount)
        }
    }

    internal fun setJobCode(
        jobCode: Long?,
    ) = intent {
        reduce {
            state.copy(jobCode = jobCode)
        }
    }

    internal fun setTechCode(
        techCode: String?,
    ) = intent {
        reduce {
            state.copy(
                techCode = techCode,
            )
        }
    }

    internal fun setName(
        name: String,
    ) = intent {
        _recruitments.clear()
        reduce {
            state.copy(
                name = name,
                page = 1,
            )
        }
        fetchRecruitments()
    }

    internal fun setRecruitmentId(
        recruitmentId: Long,
    ) = intent {
        reduce {
            state.copy(
                recruitmentId = recruitmentId,
            )
        }
        fetchRecruitmentDetails()
    }

    private fun setRecruitments(
        recruitments: List<RecruitmentUiModel>,
    ) = intent {
        _recruitments.addAll(recruitments)
        reduce {
            state.copy(recruitments = _recruitments)
        }
    }

    internal fun setPage() = intent {
        val currentPage = state.page
        reduce {
            state.copy(page = currentPage + 1)
        }
    }

    internal fun initRecruitments() = intent {
        _recruitments.clear()
        reduce {
            state.copy(
                recruitments = _recruitments,
                page = 1,
            )
        }
    }
}

data class RecruitmentUiModel(
    val recruitId: Long,
    val companyName: String,
    val companyProfileUrl: String,
    val trainPay: Long,
    val military: Boolean,
    val totalHiring: Long,
    val jobCodeList: String,
    var bookmarked: Boolean,
)

internal fun RecruitmentEntity.toModel() = RecruitmentUiModel(
    recruitId = this.recruitId,
    companyName = this.companyName,
    companyProfileUrl = JobisUrl.imageUrl + this.companyProfileUrl,
    trainPay = this.trainPay,
    military = this.military,
    totalHiring = this.totalHiring,
    jobCodeList = this.jobCodeList,
    bookmarked = this.bookmarked,
)