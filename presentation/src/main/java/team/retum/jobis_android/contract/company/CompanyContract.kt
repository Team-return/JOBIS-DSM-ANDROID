package team.retum.jobis_android.contract.company

import team.retum.domain.entity.company.CompanyDetailsEntity
import team.retum.domain.entity.company.CompanyEntity
import team.retum.domain.entity.company.ReviewableCompanyEntity
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State
import java.util.Collections.emptyList

sealed class CompanySideEffect : SideEffect {
    object NotFoundCompany : CompanySideEffect()
    class Exception(val message: String) : CompanySideEffect()
}

data class CompanyState(
    var companies: MutableList<CompanyEntity> = mutableListOf(),
    var page: Int = 1,
    var name: String? = null,
    var companyId: Long = 0,
    val reviewableCompanies: List<ReviewableCompanyEntity> = emptyList(),
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
        attachments = emptyList(),
        serviceName = "",
        businessArea = "",
    ),
) : State