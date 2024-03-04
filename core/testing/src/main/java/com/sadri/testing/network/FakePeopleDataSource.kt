package com.sadri.testing.network

import com.sadri.model.AppException
import com.sadri.network.PeopleDataSource
import com.sadri.network.model.PeopleItemResponse
import com.sadri.network.model.PeopleResultResponse
import java.io.IOException

class FakePeopleDataSource : PeopleDataSource {
  override suspend fun getPeople(name: String, page: Int): Result<PeopleResultResponse> {
    return when (name) {
      SUCCESSFUL_RESULT_QUERY -> {
        peopleResultResponse
      }
      SUCCESSFUL_RESULT_PAGE_2_QUERY -> {
        peopleResultResponseNextPage
      }
      EMPTY_RESULT_QUERY -> {
        emptyResultResponse
      }
      FAILURE_RESULT_QUERY -> {
        resultError
      }
      else -> {
        throw IllegalStateException("Unknown query")
      }
    }
  }

  companion object {
    const val SUCCESSFUL_RESULT_QUERY = "success"
    const val SUCCESSFUL_RESULT_PAGE_2_QUERY = "success - page 2"
    const val EMPTY_RESULT_QUERY = "empty"
    const val FAILURE_RESULT_QUERY = "failure"

    val peopleResultResponse = Result.success(
      PeopleResultResponse(
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
    )

    val peopleResultResponseNextPage = Result.success(
      PeopleResultResponse(
        count = 58,
        next = "https://swapi.dev/api/people/?search=a&page=3",
        previous = "https://swapi.dev/api/people/?search=a&page=1",
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
    )

    val emptyResultResponse = Result.success(
      PeopleResultResponse(
        count = 0,
        next = null,
        previous = null,
        listOf()
      )
    )

    val exception = AppException.IO(IOException())
    val resultError = Result.failure<PeopleResultResponse>(exception)


  }
}