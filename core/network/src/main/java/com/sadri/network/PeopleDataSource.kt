package com.sadri.network

import com.sadri.network.model.PeopleResultResponse

interface PeopleDataSource {
  suspend fun getPeople(name: String, page: Int): Result<PeopleResultResponse>
}