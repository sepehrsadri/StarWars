package com.sadri.domain

import com.sadri.data.repository.PeopleRepository
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class GetPeopleUseCaseTest {

  private val repository = spyk<PeopleRepository>()
  private lateinit var getPeopleUseCase: GetPeopleUseCase

  @Before
  fun setup() {
    getPeopleUseCase = DefaultGetPeopleUseCase(repository)
  }

  @Test
  fun `given invoke then repository get people get called`() {
    // Given
    val query = "a"

    // When
    getPeopleUseCase.invoke(query)

    // Then
    verify { repository.getPeople(query) }
  }

}