package com.sadri.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sadri.data.paging.PeoplePagingSource
import com.sadri.model.PeopleEntity
import com.sadri.network.PeopleDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultPeopleRepository @Inject constructor(
  private val dataSource: PeopleDataSource
) : PeopleRepository {
  override fun getPeople(name: String): Flow<PagingData<PeopleEntity>> {
    return Pager(
      config = PagingConfig(pageSize = 20, prefetchDistance = 2, enablePlaceholders = false)
    ) {
      PeoplePagingSource(
        name = name,
        dataSource = dataSource
      )
    }.flow
  }
}