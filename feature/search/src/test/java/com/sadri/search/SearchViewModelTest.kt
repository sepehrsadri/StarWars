package com.sadri.search

import androidx.paging.LoadState
import androidx.paging.testing.ErrorRecovery
import androidx.paging.testing.LoadErrorHandler
import androidx.paging.testing.asSnapshot
import com.sadri.data.mapper.asEntity
import com.sadri.data.repository.DefaultPeopleRepository
import com.sadri.data.repository.PeopleRepository
import com.sadri.domain.DefaultGetPeopleUseCase
import com.sadri.domain.GetPeopleUseCase
import com.sadri.testing.network.FakePeopleDataSource
import com.sadri.testing.util.MainDispatcherRule
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val dataSource = FakePeopleDataSource()
  private val repository: PeopleRepository = DefaultPeopleRepository(dataSource)
  private val getPeopleUseCase = DefaultGetPeopleUseCase(repository)
  private val spyPeopleUseCase = spyk<GetPeopleUseCase>(getPeopleUseCase)
  private lateinit var searchViewModel: SearchViewModel
  private lateinit var spySearchViewModel: SearchViewModel

  @Before
  fun setup() {
    searchViewModel = SearchViewModel(getPeopleUseCase)
    spySearchViewModel = SearchViewModel(spyPeopleUseCase)
  }

  @Test
  fun `given failure query then state is error`() = runTest {
    // Given
    val query = FakePeopleDataSource.FAILURE_RESULT_QUERY

    // When
    searchViewModel.search(query)

    // Then
    searchViewModel.uiState.asSnapshot(
      onError = LoadErrorHandler {
        assertTrue(it.refresh is LoadState.Error)
        return@LoadErrorHandler ErrorRecovery.RETRY
      }
    )
  }

  @Test
  fun `given success query then state has people entities`() = runTest {
    // Given
    val query = FakePeopleDataSource.SUCCESSFUL_RESULT_QUERY

    // When
    searchViewModel.search(query)

    // Then
    val peopleEntities = searchViewModel.uiState.asSnapshot()
    assertEquals(
      FakePeopleDataSource.peopleResultResponse.getOrNull()!!.results.map { it.asEntity() }.first(),
      peopleEntities.first()
    )
  }

  @Test
  fun `given empty query then use case will not be called`() = runTest {
    // Given
    val query = ""

    // When
    spySearchViewModel.search(query)

    // Then
    verify(atLeast = 0, atMost = 0) { spyPeopleUseCase.invoke("") }
  }

  @Test
  fun `given query then on retry use case will be called`() = runTest {
    // Given
    val query = "a"
    spySearchViewModel.setSearchText(query)

    // When
    spySearchViewModel.onRetry()

    // Then
    verify(atLeast = 1, atMost = 1) { spyPeopleUseCase.invoke(query) }
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  @Test
  fun `given search text many times then search invoked only once becasue of debounce`() = runTest {
    // Given
    val query = "a"

    // When
    var counter = 5
    while (counter != 0) {
      spySearchViewModel.setSearchText(query)
      counter--
      delay(100)
    }

    // Then
    advanceTimeBy(700)
    verify(atLeast = 1, atMost = 1) { spyPeopleUseCase.invoke(query) }
  }
}