package team.retum.jobis_android.util.paging.base

import androidx.paging.PagingSource
import androidx.paging.PagingState

abstract class BasicPagingSource<T : Any>: PagingSource<Int, T>(){

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition
    }
}