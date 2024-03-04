package com.sadri.data.repository

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.sadri.data.mapper.asEntity
import com.sadri.model.PeopleEntity
import com.sadri.testing.network.FakePeopleDataSource
import com.sadri.testing.util.MainDispatcherRule
import com.sadri.testing.util.collectDataForTest
import com.sadri.testing.util.collectStateForTest
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PeopleRepositoryTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val datasource = FakePeopleDataSource()
  private lateinit var repository: PeopleRepository

  @Before
  fun setup() {
    repository = DefaultPeopleRepository(datasource)
  }

  @Test
  fun `given success query then source refresh success`() = runTest {
    // Given
    val query = FakePeopleDataSource.SUCCESSFUL_RESULT_QUERY
    val peopleEntities: MutableList<PeopleEntity> = mutableListOf()

    // When
    val job = backgroundScope.launch {
      val result = repository.getPeople(query)
      result.first().collectDataForTest(
        mainDispatcherRule.testDispatcher
      ) {
        peopleEntities.addAll(it)
        cancel()
      }
    }

    // Then
    joinAll(job)
    val expectedResult =
      FakePeopleDataSource.peopleResultResponse.getOrNull()!!.results.map { it.asEntity() }
    assertEquals(
      expectedResult,
      peopleEntities
    )
  }

  @Test
  fun `given success query then source refresh loading`() = runTest {
    // Given
    val query = FakePeopleDataSource.SUCCESSFUL_RESULT_QUERY
    val captureStates = mutableListOf<CombinedLoadStates>()

    // When
    val job = backgroundScope.launch {
      var counter = 0
      val result = repository.getPeople(query)
      result.first().collectStateForTest(
        mainDispatcherRule.testDispatcher
      ) {
        if (counter + 1 == 2) {
          cancel()
        }
        counter++
        captureStates.add(it)
      }
    }

    // Then
    joinAll(job)
    assertTrue(
      captureStates.first().refresh is LoadState.Loading
    )
  }

  @Test
  fun `given failure query then source refresh failure`() = runTest {
    // Given
    val query = FakePeopleDataSource.FAILURE_RESULT_QUERY
    val captureStates = mutableListOf<CombinedLoadStates>()

    // When
    val job = backgroundScope.launch {
      var counter = 0
      val result = repository.getPeople(query)
      result.first().collectStateForTest(
        mainDispatcherRule.testDispatcher
      ) {
        if (counter + 1 == 2) {
          cancel()
        }
        counter++
        captureStates.add(it)
      }
    }

    // Then
    joinAll(job)
    assertTrue(
      captureStates.last().refresh is LoadState.Error
    )
  }
}