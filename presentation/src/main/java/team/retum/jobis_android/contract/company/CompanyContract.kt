package team.retum.jobis_android.contract.company

import team.retum.domain.entity.company.CompanyEntity
import team.retum.domain.entity.company.CompanyDetailsEntity
import team.retum.domain.entity.company.ReviewableCompanyEntity
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State
import java.util.Collections.emptyList

sealed class CompanySideEffect: SideEffect{
    object NotFoundCompany: CompanySideEffect()
    class Exception(val message: String): CompanySideEffect()
}

data class CompanyState(
    var companies: List<CompanyEntity> = mutableListOf(),
    var page: Int = 1,
    var name: String? = null,
    var companyId: Int = 0,
    val reviewableCompanies: List<ReviewableCompanyEntity> = emptyList(),
    var companyDetails: CompanyDetailsEntity = CompanyDetailsEntity(
        address1 = "",
        address2 = null,
        attachments = emptyList(),
        businessNumber = "",
        companyIntroduce = "",
        companyName = "",
        companyProfileUrl = "",
        email = "",
        fax = null,
        foundedAt = "",
        manager1 = "",
        manager2 = null,
        phoneNumber1 = "",
        phoneNumber2 = null,
        recruitmentId = null,
        representativeName = "",
        take = 0.0,
        workerNumber = 0,
        zipCode1 = "",
    ),
): State