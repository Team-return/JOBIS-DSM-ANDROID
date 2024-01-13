package team.retum.jobis_android.feature.company

import androidx.annotation.StringRes
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
import team.retum.domain.usecase.company.FetchCompanyDetailsUseCase
import team.retum.jobis_android.feature.root.BaseViewModel
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State
import java.util.Collections
import javax.inject.Inject

@HiltViewModel
class CompanyDetailsScreenViewModel
@Inject constructor(
    private val fetchCompanyDetailUseCase: FetchCompanyDetailsUseCase,
) : BaseViewModel<CompanyDetailsState, CompanyDetailsSideEffect>() {
    override val container =
        container<CompanyDetailsState, CompanyDetailsSideEffect>(CompanyDetailsState())

    internal fun fetchCompanyDetails(companyId: Long?) = intent {
        viewModelScope.launch(Dispatchers.IO) {
            fetchCompanyDetailUseCase(companyId = null).onSuccess {
                reduce { state.copy(companyDetails = it.copy(companyProfileUrl = JobisUrl.imageUrl + it.companyProfileUrl)) }
            }.onFailure { throwable ->
                postSideEffect(
                    when (throwable) {
                        is NullPointerException -> CompanyDetailsSideEffect.NotFoundCompanyDetails
                        else -> CompanyDetailsSideEffect.Exception(getStringFromException(throwable))
                    },
                )
            }
        }
    }
}

sealed interface CompanyDetailsSideEffect : SideEffect {
    object NotFoundCompanyDetails : CompanyDetailsSideEffect
    class Exception(@StringRes val message: Int) : CompanyDetailsSideEffect
}

data class CompanyDetailsState(
    var companyDetails: CompanyDetailsEntity = CompanyDetailsEntity(
        businessNumber = "",
        companyName = "",
        companyProfileUrl = "",
        companyIntroduce = "",
        mainZipCode = "",
        mainAddress = "",
        mainAddressDetail = "",
        subAddress = null,
        subAddressDetail = null,
        managerName = "",
        managerPhoneNo = "",
        subManagerName = null,
        subManagerPhoneNo = null,
        fax = null,
        email = "",
        representativeName = "",
        foundedAt = "",
        workerNumber = 0,
        take = 0f,
        recruitmentId = null,
        attachments = Collections.emptyList(),
        serviceName = "",
        businessArea = "",
    ),
) : State
