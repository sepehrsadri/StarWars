package com.sadri.data.paging

import androidx.paging.PagingSource
import com.sadri.data.mapper.asEntity
import com.sadri.model.AppException
import com.sadri.model.PeopleEntity
import com.sadri.testing.network.FakePeopleDataSource
import com.sadri.testing.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class PeoplePagingSourceTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val datasource = FakePeopleDataSource()
  private lateinit var peoplePagingSource: PeoplePagingSource

  @Test
  fun `given success query then source refresh success`() = runTest {
    // Given
    val query = FakePeopleDataSource.SUCCESSFUL_RESULT_QUERY
    peoplePagingSource = PeoplePagingSource(query, datasource)

    // When
    val result = peoplePagingSource.load(
      PagingSource.LoadParams.Refresh(
        key = 0,
        loadSize = 1,
        placeholdersEnabled = false
      )
    )

    // Then
    val expectedResult = PagingSource.LoadResult.Page(
      data = FakePeopleDataSource.peopleResultResponse.getOrNull()!!.results.map { it.asEntity() },
      prevKey = -1,
      nextKey = 1
    )
    assertEquals(
      expectedResult,
      result
    )
  }

  @Test
  fun `given empty query then source refresh failure received null`() = runTest {
    // Given
    val query = FakePeopleDataSource.EMPTY_RESULT_QUERY
    peoplePagingSource = PeoplePagingSource(query, datasource)

    // When
    val result = peoplePagingSource.load(
      PagingSource.LoadParams.Refresh(
        key = 0,
        loadSize = 1,
        placeholdersEnabled = false
      )
    )

    // Then
    val error = AppException.Empty
    val expectedResult = PagingSource.LoadResult.Error<Int, PeopleEntity>(error)
    assertEquals(
      expectedResult,
      result
    )
  }

  @Test
  fun `given failure query then source refresh failure received error`() = runTest {
    // Given
    val query = FakePeopleDataSource.FAILURE_RESULT_QUERY
    peoplePagingSource = PeoplePagingSource(query, datasource)

    // When
    val result = peoplePagingSource.load(
      PagingSource.LoadParams.Refresh(
        key = 0,
        loadSize = 1,
        placeholdersEnabled = false
      )
    )

    // Then
    val error = FakePeopleDataSource.exception
    val expectedResult = PagingSource.LoadResult.Error<Int, PeopleEntity>(error)
    assertEquals(
      expectedResult,
      result
    )
    assertEquals(
      "Something went wrong! = IO code",
      (result as PagingSource.LoadResult.Error).throwable.message
    )
  }

  @Test
  fun `given append query then paging source append success`() = runTest {
    // Given
    val query = FakePeopleDataSource.SUCCESSFUL_RESULT_PAGE_2_QUERY
    peoplePagingSource = PeoplePagingSource(query, datasource)

    // When
    val result = peoplePagingSource.load(
      PagingSource.LoadParams.Append(
        key = 2,
        loadSize = 1,
        placeholdersEnabled = false
      )
    )

    // Then
    val expectedResult = PagingSource.LoadResult.Page(
      data = FakePeopleDataSource.peopleResultResponseNextPage.getOrNull()!!.results.map { it.asEntity() },
      prevKey = 1,
      nextKey = 3
    )
    assertEquals(
      expectedResult,
      result
    )
  }
}