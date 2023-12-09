package team.retum.jobis_android.feature.recruitment

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
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
import team.retum.domain.param.recruitment.FetchRecruitmentsParam
import team.retum.domain.usecase.recruitment.FetchRecruitmentCountUseCase
import team.retum.domain.usecase.recruitment.FetchRecruitmentDetailsUseCase
import team.retum.domain.usecase.recruitment.FetchRecruitmentsUseCase
import team.retum.jobis_android.feature.root.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class RecruitmentViewModel @Inject constructor(
    private val fetchRecruitmentsUseCase: FetchRecruitmentsUseCase,
    private val fetchRecruitmentDetailsUseCase: FetchRecruitmentDetailsUseCase,
    private val fetchRecruitmentCountUseCase: FetchRecruitmentCountUseCase,
) : BaseViewModel<RecruitmentState, RecruitmentSideEffect>() {

    override val container = container<RecruitmentState, RecruitmentSideEffect>(RecruitmentState())

    private val _recruitments: SnapshotStateList<RecruitmentUiModel> = mutableStateListOf()

    internal fun addRecruitmentsDummy() = intent {
        repeat(8) {
            _recruitments.add(
                RecruitmentUiModel(
                    recruitId = 0,
                    companyName = "",
                    companyProfileUrl = "",
                    trainPay = 0,
                    military = false,
                    totalHiring = 0,
                    jobCodeList = "",
                    bookmarked = false,
                ),
            )
        }
        reduce { state.copy(recruitments = _recruitments) }
    }

    private fun clearRecruitmentsDummy() = intent {
        if (_recruitments.contains(
                RecruitmentUiModel(
                    recruitId = 0,
                    companyName = "",
                    companyProfileUrl = "",
                    trainPay = 0,
                    military = false,
                    totalHiring = 0,
                    jobCodeList = "",
                    bookmarked = false,
                ),
            )
        ) {
            _recruitments.clear()
            reduce {
                state.copy(recruitments = _recruitments)
            }
        }
    }

    internal fun fetchRecruitments() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            with(state) {
                fetchRecruitmentsUseCase(
                    fetchRecruitmentsParam = FetchRecruitmentsParam(
                        page = page,
                        jobCode = jobCode,
                        techCode = techCode,
                        name = name,
                        winterIntern = isWinterIntern,
                    ),
                ).onSuccess { it ->
                    clearRecruitmentsDummy()
                    setRecruitments(it.recruitmentEntities.map { it.toModel() })
                }.onFailure { throwable ->
                    postSideEffect(
                        sideEffect = RecruitmentSideEffect.Exception(
                            message = getStringFromException(
                                throwable = throwable,
                            ),
                        ),
                    )
                }
            }
        }
    }

    private fun fetchRecruitmentDetails() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            fetchRecruitmentDetailsUseCase(
                recruitmentId = state.recruitmentId,
            ).onSuccess {
                setRecruitmentDetails(it)
            }.onFailure { throwable ->
                postSideEffect(
                    sideEffect = RecruitmentSideEffect.Exception(
                        message = getStringFromException(throwable),
                    ),
                )
            }
        }
    }

    internal fun fetchRecruitmentCount() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            with(state) {
                fetchRecruitmentCountUseCase(
                    fetchRecruitmentsParam = FetchRecruitmentsParam(
                        page = page,
                        name = name,
                        jobCode = jobCode,
                        techCode = techCode,
                        winterIntern = isWinterIntern,
                    ),
                ).onSuccess {
                    setRecruitmentCount(it.totalPageCount)
                }
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
                        endTime = endTime.substring(0..4),
                        etc = etc,
                        hiringProgress = hiringProgress,
                        military = military,
                        pay = pay,
                        requiredGrade = requiredGrade,
                        requiredLicenses = requiredLicenses,
                        startDate = startDate,
                        startTime = startTime.substring(0..4),
                        submitDocument = submitDocument,
                        trainPay = trainPay,
                    ),
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
            state.copy(recruitments = _recruitments.toMutableStateList())
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

    internal fun resetPage() = intent {
        reduce {
            state.copy(page = 1)
        }
    }

    internal fun setIsWinterIntern(isWinterIntern: Boolean) = intent {
        reduce {
            state.copy(isWinterIntern = isWinterIntern)
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
