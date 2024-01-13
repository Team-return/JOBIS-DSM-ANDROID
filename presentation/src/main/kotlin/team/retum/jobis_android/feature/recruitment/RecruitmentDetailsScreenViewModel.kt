package team.retum.jobis_android.feature.recruitment

import androidx.annotation.StringRes
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
import team.retum.domain.usecase.recruitment.FetchRecruitmentDetailsUseCase
import team.retum.jobis_android.feature.root.BaseViewModel
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State
import javax.inject.Inject

@HiltViewModel
internal class RecruitmentDetailsScreenViewModel @Inject constructor(
    private val fetchRecruitmentDetailsUseCase: FetchRecruitmentDetailsUseCase,
) : BaseViewModel<RecruitmentDetailsState, RecruitmentDetailsSideEffect>() {
    override val container =
        container<RecruitmentDetailsState, RecruitmentDetailsSideEffect>(RecruitmentDetailsState())

    internal fun fetchRecruitmentDetails(recruitmentId: Long?) = intent {
        viewModelScope.launch(Dispatchers.IO) {
            fetchRecruitmentDetailsUseCase(recruitmentId).onSuccess { detail ->
                with(detail) {
                    reduce { state.copy(details = copy(companyProfileUrl = JobisUrl.imageUrl + companyProfileUrl)) }
                }
            }.onFailure { throwable ->
                when (throwable) {
                    is NullPointerException -> {
                        postSideEffect(RecruitmentDetailsSideEffect.RecruitmentNotFound)
                    }

                    else -> postSideEffect(
                        sideEffect = RecruitmentDetailsSideEffect.Exception(
                            message = getStringFromException(throwable),
                        ),
                    )
                }
            }
        }
    }
}

data class RecruitmentDetailsState(
    var recruitmentId: Long = 0L,
    var details: RecruitmentDetailsEntity = RecruitmentDetailsEntity(
        areas = emptyList(),
        benefits = null,
        companyId = 0,
        companyName = "",
        companyProfileUrl = "",
        endDate = "",
        endTime = "",
        etc = null,
        hiringProgress = emptyList(),
        military = false,
        pay = "",
        requiredGrade = null,
        requiredLicenses = null,
        startDate = "",
        startTime = "",
        submitDocument = "",
        trainPay = 0,
    ),
    val recruitments: SnapshotStateList<RecruitmentEntity> = mutableStateListOf(),
) : State

sealed interface RecruitmentDetailsSideEffect : SideEffect {
    object RecruitmentNotFound : RecruitmentDetailsSideEffect
    class Exception(@StringRes val message: Int) : RecruitmentDetailsSideEffect
}
