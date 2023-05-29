package team.retum.jobis_android.viewmodel.recruitment

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.entity.recruitment.RecruitmentDetailsEntity
import team.retum.domain.param.recruitment.FetchRecruitmentListParam
import team.retum.domain.usecase.recruitment.BookmarkRecruitmentUseCase
import team.retum.domain.usecase.recruitment.FetchRecruitmentDetailsUseCase
import team.retum.domain.usecase.recruitment.FetchRecruitmentListUseCase
import team.retum.jobis_android.contract.RecruitmentEvent
import team.retum.jobis_android.contract.RecruitmentSideEffect
import team.retum.jobis_android.contract.RecruitmentState
import team.retum.jobis_android.util.mvi.Event
import team.retum.jobis_android.viewmodel.BaseViewModel
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
internal class RecruitmentViewModel @Inject constructor(
    private val fetchRecruitmentListUseCase: FetchRecruitmentListUseCase,
    private val bookmarkRecruitmentUseCase: BookmarkRecruitmentUseCase,
    private val fetchRecruitmentDetailsUseCase: FetchRecruitmentDetailsUseCase,
) : BaseViewModel<RecruitmentState, RecruitmentSideEffect>() {

    override val container = container<RecruitmentState, RecruitmentSideEffect>(RecruitmentState())

    override fun sendEvent(event: Event) {
        when (event) {
            is RecruitmentEvent.FetchRecruitments -> {
                fetchRecruitments(
                    page = event.page,
                    code = event.code,
                    company = event.company,
                )
            }

            is RecruitmentEvent.BookmarkRecruitment -> {
                bookmarkRecruitment(
                    recruitmentId = event.recruitmentId,
                )
            }
        }
    }

    private fun fetchRecruitments(
        page: Int,
        code: Long?,
        company: String?,
    ) = intent {
        viewModelScope.launch(Dispatchers.IO) {
            fetchRecruitmentListUseCase(
                fetchRecruitmentListParam = FetchRecruitmentListParam(
                    page = page,
                    code = code,
                    company = company,
                )
            ).onSuccess {
                postSideEffect(
                    sideEffect = RecruitmentSideEffect.SuccessFetchRecruitmentsSideEffect(
                        recruitmentsEntity = it,
                    )
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

    private fun bookmarkRecruitment(
        recruitmentId: Long,
    ) = intent {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkRecruitmentUseCase(
                recruitmentId = recruitmentId,
            ).onFailure { throwable ->
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
                        preferentialTreatment = preferentialTreatment,
                        requiredGrade = requiredGrade,
                        workHours = workHours,
                        requiredLicenses = requiredLicenses,
                        hiringProgress = hiringProgress,
                        trainPay = trainPay,
                        pay = pay,
                        benefits = benefits,
                        military = military,
                        submitDocument = submitDocument,
                        startDate = startDate,
                        endDate = endDate,
                        etc = etc,
                    )
                )
            }
        }
    }

}