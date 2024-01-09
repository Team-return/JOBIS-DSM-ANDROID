package team.retum.jobis_android.feature.recruitment

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.data.remote.url.JobisUrl
import team.retum.domain.usecase.recruitment.FetchRecruitmentDetailsUseCase
import team.retum.jobis_android.feature.root.BaseViewModel
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
