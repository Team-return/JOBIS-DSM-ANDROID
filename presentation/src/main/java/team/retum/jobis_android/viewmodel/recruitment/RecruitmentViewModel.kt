package team.retum.jobis_android.viewmodel.recruitment

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.param.FetchRecruitmentListParam
import team.retum.domain.usecase.FetchRecruitmentListUseCase
import team.retum.jobis_android.contract.RecruitmentEvent
import team.retum.jobis_android.contract.RecruitmentSideEffect
import team.retum.jobis_android.contract.RecruitmentState
import team.retum.jobis_android.util.mvi.Event
import team.retum.jobis_android.viewmodel.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class RecruitmentViewModel @Inject constructor(
    private val fetchRecruitmentListUseCase: FetchRecruitmentListUseCase,
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
                        recruitmentListEntity = it,
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
}