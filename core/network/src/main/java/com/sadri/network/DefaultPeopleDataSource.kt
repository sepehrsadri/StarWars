package com.sadri.network

import com.sadri.network.api.PeopleService
import com.sadri.network.model.PeopleResultResponse
import com.sadri.network.utils.safeApiCall
import javax.inject.Inject

class DefaultPeopleDataSource @Inject constructor(
  private val peopleService: PeopleService
) : PeopleDataSource {

  override suspend fun getPeople(name: String, page: Int): Result<PeopleResultResponse> =
    safeApiCall {
      peopleService.getPeople(name, page)
    }
}