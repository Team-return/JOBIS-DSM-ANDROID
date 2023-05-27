package team.retum.domain.repository

import team.retum.domain.entity.CompaniesEntity
import team.retum.domain.param.FetchCompaniesParam

interface CompanyRepository {
    suspend fun fetchCompanies(
        fetchCompaniesParam: FetchCompaniesParam,
    ): CompaniesEntity
}