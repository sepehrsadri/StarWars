package com.sadri.domain

import com.sadri.data.repository.PeopleRepository
import com.sadri.model.PeopleResultEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPeopleUseCase @Inject constructor(
  private val repository: PeopleRepository
) {
  operator fun invoke(name: String): Flow<Result<PeopleResultEntity>> =
    repository.getPeople(name)
}