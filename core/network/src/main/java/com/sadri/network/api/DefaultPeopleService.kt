package com.sadri.network.api

import com.sadri.network.model.PeopleResultResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DefaultPeopleService : PeopleService {
  @GET(value = "people")
  override suspend fun getPeople(
    @Query("search") name: String,
    @Query("page") page: Int
  ): PeopleResultResponse
}