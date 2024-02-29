package com.sadri.network.api

import com.sadri.network.model.PeopleResultResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PeopleService {
  @GET(value = "people")
  suspend fun getPeople(
    @Query("search") name: String,
    @Query("page") page: Int
  ): PeopleResultResponse
}