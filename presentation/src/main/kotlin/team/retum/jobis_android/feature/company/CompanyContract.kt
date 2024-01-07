package team.retum.jobis_android.feature.company

import androidx.annotation.StringRes
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import team.retum.domain.entity.company.CompanyDetailsEntity
import team.retum.domain.entity.company.CompanyEntity
import team.retum.domain.entity.company.ReviewableCompanyEntity
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State
import java.util.Collections.emptyList

sealed class CompanySideEffect : SideEffect {
    object NotFoundCompany : CompanySideEffect()
    class Exception(@StringRes val message: Int) : CompanySideEffect()
}

data class CompanyState(
    var companies: SnapshotStateList<CompanyEntity> = mutableStateListOf(),
    var page: Int = 1,
    var companyCount: Long = 0,
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
