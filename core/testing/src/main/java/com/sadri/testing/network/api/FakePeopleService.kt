package com.sadri.testing.network.api

import com.sadri.network.api.PeopleService
import com.sadri.network.model.PeopleItemResponse
import com.sadri.network.model.PeopleResultResponse
import java.io.IOException

class FakePeopleService : PeopleService {

  override suspend fun getPeople(name: String, page: Int): PeopleResultResponse {
    return when (name) {
      SUCCESS_RESULT_RESPONSE_QUERY -> {
        peopleResultResponse
      }
      FAILURE_RESULT_RESPONSE_QUERY -> {
        throw IOException(FAILURE_EXCEPTION_MESSAGE)
      }
      else -> {
        throw IllegalStateException("Unknown query")
      }
    }
  }

  companion object {
    const val SUCCESS_RESULT_RESPONSE_QUERY = "success"
    const val FAILURE_RESULT_RESPONSE_QUERY = "failure"

    const val FAILURE_EXCEPTION_MESSAGE = "404 code"

    val peopleResultResponse = PeopleResultResponse(
      count = 58,
      next = "https://swapi.dev/api/people/?search=a&page=2",
      previous = null,
      results = listOf(
        PeopleItemResponse(
          name = "Luke Skywalker",
          birth_year = "19BBY",
          height = "172",
          films = listOf(),
          species = listOf(),
          homeworld = "https://swapi.dev/api/planets/1/"
        )
      )
    )

  }

}