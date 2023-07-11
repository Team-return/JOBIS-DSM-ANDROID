package team.retum.jobis_android.viewmodel.recruitment

import androidx.lifecycle.viewModelScope
import com.jobis.jobis_android.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.entity.recruitment.RecruitmentDetailsEntity
import team.retum.domain.entity.recruitment.RecruitmentEntity
import team.retum.domain.param.recruitment.FetchRecruitmentListParam
import team.retum.domain.usecase.recruitment.FetchRecruitmentDetailsUseCase
import team.retum.domain.usecase.recruitment.FetchRecruitmentListUseCase
import team.retum.jobis_android.contract.RecruitmentSideEffect
import team.retum.jobis_android.contract.RecruitmentState
import team.retum.jobis_android.util.mvi.Event
import team.retum.jobis_android.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class RecruitmentViewModel @Inject constructor(
    private val fetchRecruitmentListUseCase: FetchRecruitmentListUseCase,
    private val fetchRecruitmentDetailsUseCase: FetchRecruitmentDetailsUseCase,
) : BaseViewModel<RecruitmentState, RecruitmentSideEffect>() {

    override val container = container<RecruitmentState, RecruitmentSideEffect>(RecruitmentState())

    override fun sendEvent(event: Event) {}

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
                        companyProfileUrl = companyProfileUrl,
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

    internal fun setPage(
        page: Int,
    ) = intent {
        reduce {
            state.copy(
                page = page,
            )
        }
    }

    internal fun setJobCode(
        jobCode: Long?,
    ) = intent {
        reduce {
            state.copy(
                jobCode = jobCode,
            )
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
        reduce {
            state.copy(
                name = name,
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

    internal fun getRecruitmentDetails(): List<Pair<Int, Any?>> =
        with(container.stateFlow.value.details) {
            return listOf(
                R.string.recruitment_details_recruit_period to "$startDate ~ $endDate",
                R.string.recruitment_details_preferential_treatment to benefits,
                R.string.recruitment_details_licenses to requiredLicenses,
                R.string.recruitment_details_required_grade to requiredGrade,
                R.string.recruitment_details_worker_time to workHours,
                R.string.recruitment_details_hiring to pay,
                R.string.recruitment_details_benefits to benefits,
                R.string.recruitment_details_hiring_progress to hiringProgress,
                R.string.recruitment_details_required_documents to submitDocument,
                R.string.recruitment_details_etc to etc,
            )
        }

    private fun setRecruitments(
        recruitments: List<RecruitmentUiModel>,
    ) = intent {
        reduce {
            state.copy(
                recruitments = recruitments,
            )
        }
    }
}

data class RecruitmentUiModel(
    val recruitId: Int,
    val companyName: String,
    val companyProfileUrl: String,
    val trainPay: Int,
    val military: Boolean,
    val totalHiring: Int,
    val jobCodeList: String,
    var bookmarked: Boolean,
)

internal fun RecruitmentEntity.toModel() = RecruitmentUiModel(
    recruitId = this.recruitId,
    companyName = this.companyName,
    companyProfileUrl = this.companyProfileUrl,
    trainPay = this.trainPay,
    military = this.military,
    totalHiring = this.totalHiring,
    jobCodeList = this.jobCodeList,
    bookmarked = this.bookmarked,
)