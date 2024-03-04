package com.sadri.network

import com.sadri.testing.network.api.FakePeopleService
import com.sadri.network.api.PeopleService
import com.sadri.testing.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PeopleDataSourceTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val peopleService: PeopleService = FakePeopleService()
  private lateinit var dataSource: DefaultPeopleDataSource

  @Before
  fun setup() {
    dataSource = DefaultPeopleDataSource(peopleService)
  }

  @Test
  fun `given success query then successfully result return`() = runTest {
    // Given
    val query = FakePeopleService.SUCCESS_RESULT_RESPONSE_QUERY
    val page = 1

    // When
    val result = dataSource.getPeople(query, page)

    // Then
    assertTrue(result.isSuccess)
    assertEquals(
      FakePeopleService.peopleResultResponse.results.first(),
      result.getOrNull()!!.results.first()
    )
  }

  @Test
  fun `given failure query then failure result return`() = runTest {
    // Given
    val query = FakePeopleService.FAILURE_RESULT_RESPONSE_QUERY
    val page = 1

    // When
    val result = dataSource.getPeople(query, page)

    // Then
    assertTrue(result.isFailure)
    assertEquals(
      FakePeopleService.FAILURE_EXCEPTION_MESSAGE,
      result.exceptionOrNull()!!.message
    )
  }


}