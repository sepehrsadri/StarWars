package com.sadri.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sadri.common.toNetworkException
import com.sadri.data.mapper.asEntity
import com.sadri.model.AppException
import com.sadri.model.PeopleEntity
import com.sadri.network.PeopleDataSource

class PeoplePagingSource(
  private val name: String,
  private val dataSource: PeopleDataSource
) : PagingSource<Int, PeopleEntity>() {

  override fun getRefreshKey(state: PagingState<Int, PeopleEntity>): Int? {
    return state.anchorPosition
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PeopleEntity> {
    val currentPage = params.key ?: 1

    val response = dataSource.getPeople(
      name = name,
      page = currentPage
    )

    return when {
      response.isSuccess -> {
        val successResponse = requireNotNull(response.getOrNull())
        if (successResponse.count == 0) {
          LoadResult.Error(AppException.Empty)
        } else {
          LoadResult.Page(
            data = successResponse.results.map { it.asEntity() },
            prevKey = if (currentPage == 1) null else currentPage - 1,
            nextKey = if (successResponse.next == null) null else currentPage + 1
          )
        }
      }

      response.isFailure -> {
        val errorResponse = requireNotNull(response.exceptionOrNull())
        LoadResult.Error(errorResponse.toNetworkException())
      }

      else -> {
        LoadResult.Invalid()
      }
    }
  }
}