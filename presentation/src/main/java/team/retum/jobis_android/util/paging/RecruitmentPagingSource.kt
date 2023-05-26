package team.retum.jobis_android.util.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import team.retum.domain.entity.RecruitmentEntity
import team.retum.domain.param.FetchRecruitmentListParam
import team.retum.domain.usecase.FetchRecruitmentListUseCase

class RecruitmentPagingSource(
    private val fetchRecruitmentListUseCase: FetchRecruitmentListUseCase
) : PagingSource<Int, RecruitmentEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecruitmentEntity> {
        try {
            val startKey = params.key ?: 1

            val response = fetchRecruitmentListUseCase(
                fetchRecruitmentListParam = FetchRecruitmentListParam(
                    page = startKey,
                    code = null,
                    company = null,
                )
            )

            val items = response.getOrNull()!!.recruitmentEntities

            val prevKey = if (startKey == 1) null else startKey - 1
            val nextKey = if (items.isNotEmpty()) startKey + 1 else null

            return LoadResult.Page(
                data = items,
                prevKey = prevKey,
                nextKey = nextKey,
            )
        } catch (e: Exception) {
            return LoadResult.Error(
                throwable = e,
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RecruitmentEntity>): Int? {
        return state.anchorPosition
    }
}