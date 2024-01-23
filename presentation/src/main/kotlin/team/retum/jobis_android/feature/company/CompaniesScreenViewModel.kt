package team.retum.jobis_android.feature.company

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.data.remote.url.JobisUrl
import team.retum.domain.entity.company.CompanyEntity
import team.retum.domain.param.company.FetchCompaniesParam
import team.retum.domain.usecase.company.FetchCompaniesUseCase
import team.retum.domain.usecase.company.FetchCompanyCountUseCase
import team.retum.jobis_android.feature.root.BaseViewModel
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State
import javax.inject.Inject

@HiltViewModel
internal class CompaniesScreenViewModel @Inject constructor(
    private val fetchCompaniesUseCase: FetchCompaniesUseCase,
    private val fetchCompanyCountUseCase: FetchCompanyCountUseCase,
) : BaseViewModel<CompaniesState, CompaniesSideEffect>() {
    override val container = container<CompaniesState, CompaniesSideEffect>(CompaniesState())

    internal var companies: SnapshotStateList<CompanyEntity> = mutableStateListOf()
        private set

    var name: String? by mutableStateOf(null)
        private set

    internal fun setName(name: String) {
        this.name = name
    }

    internal fun fetchCompanies() = intent {
        reduce { state.copy(page = state.page + 1) }
        viewModelScope.launch(Dispatchers.IO) {
            fetchCompaniesUseCase(
                fetchCompaniesParam = FetchCompaniesParam(
                    page = state.page,
                    name = name,
                ),
            ).onSuccess { response ->
                companies.addAll(
                    response.companies.map {
                        val logoUrl = JobisUrl.imageUrl + it.logoUrl
                        it.copy(logoUrl = logoUrl)
                    },
                )
            }
        }
    }

    internal fun Flow<Int?>.callNextPageByPosition() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchNextPage = async {
                this@callNextPageByPosition.collect {
                    it?.run {
                        if (this == companies.lastIndex - 2) {
                            fetchCompanies()
                        }
                    }
                }
            }
            fetchNextPage.start()
            with(container) {
                launch {
                    stateFlow.collect {
                        if (it.page == state.totalPage && state.totalPage != 0L) {
                            fetchNextPage.cancel()
                        }
                    }
                }
                launch {
                    sideEffectFlow.collect { sideEffect: SideEffect ->
                        when (sideEffect) {
                            is CompaniesSideEffect.StartFetchNextPage -> {
                                fetchNextPage.start()
                            }
                        }
                    }
                }
            }
        }
    }

    internal fun fetchCompanyCount() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            fetchCompanyCountUseCase(
                fetchCompaniesParam = FetchCompaniesParam(
                    page = state.page,
                    name = name,
                ),
            ).onSuccess {
                reduce { state.copy(totalPage = it.totalPageCount) }
            }
        }
    }
}

internal data class CompaniesState(
    val page: Long = 0,
    val totalPage: Long = 0,
) : State

internal sealed interface CompaniesSideEffect : SideEffect {
    object StartFetchNextPage : CompaniesSideEffect
}
