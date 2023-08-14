package team.retum.jobis_android.viewmodel.company

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.entity.company.CompanyDetailsEntity
import team.retum.domain.entity.company.CompanyEntity
import team.retum.domain.exception.NotFoundException
import team.retum.domain.param.company.FetchCompaniesParam
import team.retum.domain.usecase.company.FetchCompaniesUseCase
import team.retum.domain.usecase.company.FetchCompanyDetailsUseCase
import team.retum.jobis_android.contract.company.CompanySideEffect
import team.retum.jobis_android.contract.company.CompanyState
import team.retum.jobis_android.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CompanyViewModel @Inject constructor(
    private val fetchCompaniesUseCase: FetchCompaniesUseCase,
    private val fetchCompanyDetailUseCase: FetchCompanyDetailsUseCase,
) : BaseViewModel<CompanyState, CompanySideEffect>() {

    init {
        fetchCompanies()
    }

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

    internal fun fetchCompanyDetails() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            fetchCompanyDetailUseCase(
                companyId = state.companyId,
            ).onSuccess {
                setCompanyDetails(
                    companyDetailsEntity = it
                )
            }.onFailure { throwable ->
                when (throwable) {
                    is NotFoundException -> {
                        postSideEffect(
                            sideEffect = CompanySideEffect.NotFoundCompany
                        )
                    }

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
        fetchCompanies()
    }

    internal fun setCompanyId(
        companyId: Int,
    ) = intent {
        reduce {
            state.copy(
                companyId = companyId,
            )
        }
    }

    private fun setCompanyDetails(
        companyDetailsEntity: CompanyDetailsEntity,
    ) = intent {
        with(companyDetailsEntity) {
            reduce {
                state.copy(
                    companyDetails = CompanyDetailsEntity(
                        address1 = address1,
                        address2 = address2,
                        attachments = attachments,
                        businessNumber = businessNumber,
                        companyIntroduce = companyIntroduce,
                        companyName = companyName,
                        companyProfileUrl = companyProfileUrl,
                        email = email,
                        fax = fax,
                        foundedAt = foundedAt,
                        manager1 = manager1,
                        manager2 = manager2,
                        phoneNumber1 = phoneNumber1,
                        phoneNumber2 = phoneNumber2,
                        recruitmentId = recruitmentId,
                        representativeName = representativeName,
                        take = take,
                        workerNumber = workerNumber,
                        zipCode1 = zipCode1,
                    )
                )
            }
        }
    }
}