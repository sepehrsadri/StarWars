package com.sadri.network.api

import com.sadri.network.model.PeopleResultResponse

interface PeopleService {
  suspend fun getPeople(
    name: String,
    page: Int
  ): PeopleResultResponse
}