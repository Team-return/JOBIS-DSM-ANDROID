package team.retum.jobis_android.viewmodel.company

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.entity.CompanyEntity
import team.retum.domain.exception.NotFoundException
import team.retum.domain.param.FetchCompaniesParam
import team.retum.domain.usecase.FetchCompaniesUseCase
import team.retum.jobis_android.contract.CompanySideEffect
import team.retum.jobis_android.contract.CompanyState
import team.retum.jobis_android.util.mvi.Event
import team.retum.jobis_android.viewmodel.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CompanyViewModel @Inject constructor(
    private val fetchCompaniesUseCase: FetchCompaniesUseCase,
) : BaseViewModel<CompanyState, CompanySideEffect>() {


    override fun sendEvent(event: Event) {}

    override val container = container<CompanyState, CompanySideEffect>(CompanyState())

    internal fun fetchCompanies() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            fetchCompaniesUseCase(
                fetchCompaniesParam = FetchCompaniesParam(
                    page = state.page,
                    name = state.name,
                )
            ).onSuccess {
                setCompanies(
                    companies = it.companies,
                )
            }.onFailure { throwable ->
                when (throwable) {
                    is NotFoundException -> postSideEffect(
                        sideEffect = CompanySideEffect.NotFoundCompany,
                    )

                    else -> {
                        postSideEffect(
                            sideEffect = CompanySideEffect.Exception(
                                message = getStringFromException(
                                    throwable = throwable,
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    private fun setCompanies(
        companies: List<CompanyEntity>,
    ) = intent {
        reduce {
            state.copy(
                companies = companies,
            )
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

    internal fun setCompanyName(
        name: String,
    ) = intent {
        reduce {
            state.copy(
                name = name,
            )
        }
    }
}