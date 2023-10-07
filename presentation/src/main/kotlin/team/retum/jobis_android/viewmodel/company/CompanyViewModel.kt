package team.retum.jobis_android.viewmodel.company

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
import team.retum.domain.entity.company.CompanyDetailsEntity
import team.retum.domain.entity.company.CompanyEntity
import team.retum.domain.entity.company.ReviewableCompanyEntity
import team.retum.domain.exception.NotFoundException
import team.retum.domain.param.company.FetchCompaniesParam
import team.retum.domain.usecase.company.FetchCompaniesUseCase
import team.retum.domain.usecase.company.FetchCompanyCountUseCase
import team.retum.domain.usecase.company.FetchCompanyDetailsUseCase
import team.retum.domain.usecase.company.FetchReviewableCompaniesUseCase
import team.retum.jobis_android.contract.company.CompanySideEffect
import team.retum.jobis_android.contract.company.CompanyState
import team.retum.jobis_android.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CompanyViewModel @Inject constructor(
    private val fetchCompaniesUseCase: FetchCompaniesUseCase,
    private val fetchCompanyDetailUseCase: FetchCompanyDetailsUseCase,
    private val fetchReviewableCompaniesUseCase: FetchReviewableCompaniesUseCase,
    private val fetchCompanyCountUseCase: FetchCompanyCountUseCase,
) : BaseViewModel<CompanyState, CompanySideEffect>() {

    override val container = container<CompanyState, CompanySideEffect>(CompanyState())

    init {
        fetchCompanies()
        fetchCompanyCount()
    }

    private val _companies: SnapshotStateList<CompanyEntity> = mutableStateListOf()

    internal fun fetchCompanies() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            fetchCompaniesUseCase(
                fetchCompaniesParam = FetchCompaniesParam(
                    page = state.page,
                    name = state.name,
                ),
            ).onSuccess { it ->
                setCompanies(companies = it.companies.map { it.copy(logoUrl = JobisUrl.imageUrl + it.logoUrl) })
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
                                ),
                            ),
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
                setCompanyDetails(it)
            }.onFailure { throwable ->
                when (throwable) {
                    is NotFoundException -> {
                        postSideEffect(
                            sideEffect = CompanySideEffect.NotFoundCompany,
                        )
                    }

                    else -> {
                        postSideEffect(
                            sideEffect = CompanySideEffect.Exception(
                                message = getStringFromException(
                                    throwable = throwable,
                                ),
                            ),
                        )
                    }
                }
            }
        }
    }

    internal fun fetchReviewableCompanies() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            fetchReviewableCompaniesUseCase().onSuccess {
                setReviewableCompanies(it.companies)
            }
        }
    }

    private fun fetchCompanyCount() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            fetchCompanyCountUseCase(
                fetchCompaniesParam = FetchCompaniesParam(
                    page = state.page,
                    name = state.name,
                ),
            ).onSuccess {
                setCompanyCount(it.totalPageCount)
            }
        }
    }

    private fun setCompanies(
        companies: List<CompanyEntity>,
    ) = intent {
        _companies.addAll(companies)
        reduce {
            state.copy(companies = _companies)
        }
    }

    private fun setCompanyCount(
        companyCount: Long,
    ) = intent {
        reduce {
            state.copy(companyCount = companyCount)
        }
    }

    internal fun setPage() = intent {
        val currentPage = state.page
        reduce {
            state.copy(page = currentPage + 1)
        }
    }

    internal fun setCompanyName(
        name: String,
    ) = intent {
        _companies.clear()
        reduce {
            state.copy(
                name = name,
                page = 1,
            )
        }
        fetchCompanies()
    }

    internal fun setCompanyId(
        companyId: Long,
    ) = intent {
        reduce {
            state.copy(
                companyId = companyId,
            )
        }
    }

    private fun setReviewableCompanies(reviewableCompanies: List<ReviewableCompanyEntity>) =
        intent {
            reduce {
                state.copy(reviewableCompanies = reviewableCompanies)
            }
        }

    private fun setCompanyDetails(
        companyDetailsEntity: CompanyDetailsEntity,
    ) = intent {
        with(companyDetailsEntity) {
            reduce {
                state.copy(
                    companyDetails = CompanyDetailsEntity(
                        businessNumber = businessNumber,
                        companyName = companyName,
                        companyProfileUrl = JobisUrl.imageUrl + companyProfileUrl,
                        companyIntroduce = companyIntroduce,
                        mainZipCode = mainZipCode,
                        mainAddress = (mainAddress + mainAddressDetail).toMainAddress(),
                        mainAddressDetail = mainAddressDetail,
                        subAddress = subAddress,
                        subAddressDetail = subAddressDetail,
                        managerName = managerName,
                        managerPhoneNo = managerPhoneNo,
                        subManagerName = subManagerName,
                        subManagerPhoneNo = subManagerPhoneNo,
                        fax = fax,
                        email = email,
                        representativeName = representativeName,
                        foundedAt = foundedAt,
                        workerNumber = workerNumber,
                        take = take,
                        recruitmentId = recruitmentId,
                        attachments = attachments,
                        serviceName = serviceName,
                        businessArea = businessArea,
                    ),
                )
            }
        }
    }
}

private fun String.toMainAddress() = this.replace("/", "")
