package team.retum.data.repository

import team.retum.data.remote.datasource.declaration.CompanyDataSource
import team.retum.data.remote.response.toEntity
import team.retum.domain.entity.CompaniesEntity
import team.retum.domain.param.FetchCompaniesParam
import team.retum.domain.repository.CompanyRepository
import javax.inject.Inject

class CompanyRepositoryImpl @Inject constructor(
    private val companyDataSource: CompanyDataSource,
) : CompanyRepository {
    override suspend fun fetchCompanies(
        fetchCompaniesParam: FetchCompaniesParam,
    ): CompaniesEntity = companyDataSource.fetchCompanies(
        page = fetchCompaniesParam.page,
        name = fetchCompaniesParam.name,
    ).toEntity()
}