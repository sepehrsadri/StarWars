package com.sadri.data.repository

import com.sadri.common.toNetworkException
import com.sadri.data.mapper.asEntity
import com.sadri.model.AppException
import com.sadri.model.PeopleResultEntity
import com.sadri.network.PeopleDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultPeopleRepository @Inject constructor(
  private val dataSource: PeopleDataSource
) : PeopleRepository {
  override fun getPeople(name: String): Flow<Result<PeopleResultEntity>> {
    return flow {
      dataSource.getPeople(name)
        .onSuccess { response ->
          if (response.count == 0) {
            emit(Result.failure(AppException.Empty))
          } else {
            emit(Result.success(response.asEntity()))
          }
        }
        .onFailure { emit(Result.failure(it.toNetworkException())) }
    }
  }
}