package team.retum.jobis_android.viewmodel.code

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.entity.code.CodeEntity
import team.retum.domain.param.code.FetchCodesParam
import team.retum.domain.param.code.Type
import team.retum.domain.usecase.code.FetchCodesUseCase
import team.retum.jobis_android.contract.CodeSideEffect
import team.retum.jobis_android.contract.CodeState
import team.retum.jobis_android.util.mvi.Event
import team.retum.jobis_android.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class CodeViewModel @Inject constructor(
    private val fetchCodesUseCase: FetchCodesUseCase,
) : BaseViewModel<CodeState, CodeSideEffect>() {

    override val container = container<CodeState, CodeSideEffect>(CodeState())

    override fun sendEvent(event: Event) {}

    internal fun fetchCodes() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            fetchCodesUseCase(
                fetchCodesParam = FetchCodesParam(
                    keyword = state.keyword,
                    type = state.type,
                    parentCode = state.parentCode,
                )
            ).onSuccess {
                when (state.type) {
                    Type.JOB -> setJobs(
                        jobs = it.codes,
                    )

                    Type.TECH -> setTechs(
                        techs = it.codes,
                    )

                    Type.BUSINESS_AREA -> setBusinessAreas(
                        businessAreas = it.codes,
                    )
                }
            }
        }
    }

    private fun setJobs(
        jobs: List<CodeEntity>,
    ) = intent {
        reduce {
            state.copy(
                jobs = jobs,
            )
        }
    }

    private fun setTechs(
        techs: List<CodeEntity>,
    ) = intent {
        reduce {
            state.copy(
                techs = techs,
            )
        }
    }

    private fun setBusinessAreas(
        businessAreas: List<CodeEntity>,
    ) = intent {
        reduce {
            state.copy(
                businessAreas = businessAreas,
            )
        }
    }

    internal fun setKeyword(
        keyword: String,
    ) = intent {
        reduce {
            state.copy(
                keyword = keyword,
            )
        }
    }

    internal fun setType(
        type: Type,
    ) = intent {
        reduce {
            state.copy(
                type = type,
            )
        }
    }

    internal fun setParentCode(
        parentCode: Long,
    ) = intent {
        reduce {
            state.copy(
                parentCode = parentCode,
            )
        }
    }
}