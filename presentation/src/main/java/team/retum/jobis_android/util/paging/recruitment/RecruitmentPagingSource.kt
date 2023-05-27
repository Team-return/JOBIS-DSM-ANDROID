package team.retum.jobis_android.util.paging.recruitment

import android.util.Log
import androidx.paging.PagingState
import kotlinx.coroutines.delay
import team.retum.domain.entity.RecruitmentEntity
import team.retum.domain.param.FetchRecruitmentListParam
import team.retum.domain.usecase.FetchRecruitmentListUseCase
import team.retum.jobis_android.util.paging.base.BasicPagingSource
import kotlin.math.max

class RecruitmentPagingSource(
    private val fetchRecruitmentListUseCase: FetchRecruitmentListUseCase
) : BasicPagingSource<RecruitmentEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecruitmentEntity> {

        try {

            val startKey = params.key ?: 1

            val customKey = if (startKey == 1) startKey
            else startKey - (params.loadSize * startKey / params.loadSize) + 2

            val response = fetchRecruitmentListUseCase(
                fetchRecruitmentListParam = FetchRecruitmentListParam(
                    page = customKey,
                    code = null,
                    company = null,
                )
            )

            val items = response.getOrNull()!!.recruitmentEntities

            val firstValue = if(startKey == 1) startKey
            else customKey + 11

            val range = firstValue until params.loadSize

            Log.d("TEST", range.toString())

            if (startKey != 1) delay(2000)

            return LoadResult.Page(
                data = range.map {
                    RecruitmentEntity(
                        recruitId = items[it].recruitId,
                        companyName = items[it].companyName,
                        companyProfileUrl = items[it].companyProfileUrl,
                        trainPay = items[it].trainPay,
                        military = items[it].military,
                        totalHiring = items[it].totalHiring,
                        jobCodeList = items[it].jobCodeList,
                        bookmarked = items[it].bookmarked,
                    )
                },
//                data = range.map {
//                    RecruitmentEntity(
//                        recruitId = 1,
//                        companyName = it.toString(),
//                        companyProfileUrl = "items[it].companyProfileUrl",
//                        trainPay = 1,
//                        military = false,
//                        totalHiring = 1,
//                        jobCodeList = "items[it].jobCodeList",
//                        bookmarked = false,
//                    )
//                },
                prevKey = when (startKey) {
                    1 -> null
                    else -> when (val prevKey = max(1, range.first - params.loadSize)) {
                        1 -> null
                        else -> prevKey
                    }
                },
                nextKey = range.last + 1,
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