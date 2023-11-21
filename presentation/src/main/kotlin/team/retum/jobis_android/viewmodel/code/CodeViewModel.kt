package team.retum.jobis_android.viewmodel.code

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.entity.code.CodeEntity
import team.retum.domain.enums.Type
import team.retum.domain.param.code.FetchCodesParam
import team.retum.domain.usecase.code.FetchCodesUseCase
import team.retum.jobis_android.contract.code.CodeSideEffect
import team.retum.jobis_android.contract.code.CodeState
import team.retum.jobis_android.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class CodeViewModel @Inject constructor(
    private val fetchCodesUseCase: FetchCodesUseCase,
) : BaseViewModel<CodeState, CodeSideEffect>() {

    override val container = container<CodeState, CodeSideEffect>(CodeState())

    private val _techs = mutableListOf<CodeEntity>()

    internal fun fetchCodes() = intent {
        val type = state.type

        viewModelScope.launch(Dispatchers.IO) {
            fetchCodesUseCase(
                fetchCodesParam = FetchCodesParam(
                    keyword = state.keyword,
                    type = type,
                    parentCode = state.selectedJobCode,
                ),
            ).onSuccess {
                when (type) {
                    Type.JOB -> setJobs(jobs = it.codes)

                    Type.TECH -> {
                        with(_techs) {
                            clear()
                            addAll(it.codes)
                            setTechs(techs = this)
                        }
                    }

                    Type.BUSINESS_AREA -> setBusinessAreas(businessAreas = it.codes)
                }
            }
        }
    }

    private fun setJobs(
        jobs: List<CodeEntity>,
    ) = intent {
        reduce {
            state.copy(jobs = jobs.sortedByDescending { it.keyword.length })
        }
    }

    private fun setTechs(
        techs: MutableList<CodeEntity>,
    ) = intent {
        reduce {
            state.copy(techs = techs.toMutableStateList())
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
        searchTechCode(keyword)
    }

    internal fun setType(
        type: Type,
    ) = intent {
        reduce {
            state.copy(type = type)
        }
    }

    internal fun setParentCode(
        parentCode: Long?,
    ) = intent {
        reduce {
            state.copy(selectedJobCode = parentCode)
        }
    }

    internal fun onSelectTech(
        code: Long,
        keyword: String,
    ) = intent {
        val tech = code to keyword

        with(state.selectedTechs) {
            if (contains(tech)) {
                remove(tech)
            } else {
                add(tech)
            }
        }
        reduce {
            state.copy(selectedTechs = state.selectedTechs)
        }
    }

    private fun searchTechCode(
        keyword: String,
    ) = intent {
        val resultList = mutableListOf<CodeEntity>()

        _techs.filter {
            keyword.length <= it.keyword.length && keyword.uppercase() == it.keyword.substring(
                keyword.indices,
            ).uppercase()
        }.map {
            resultList.add(it)
        }

        setTechs(
            techs = if (keyword.isBlank()) {
                _techs
            } else {
                resultList
            },
        )
    }
}
