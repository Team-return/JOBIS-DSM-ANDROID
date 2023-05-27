package team.retum.jobis_android.contract

import team.retum.domain.entity.CompanyEntity
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State
import java.util.Collections.emptyList

sealed class CompanySideEffect: SideEffect{
    object NotFoundCompany: CompanySideEffect()
    class Exception(
        val message: String,
    ): CompanySideEffect()
}

data class CompanyState(
    var companies: List<CompanyEntity> = emptyList(),
    var page: Int = 1,
    var name: String? = null,
): State