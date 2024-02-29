package com.sadri.domain

import androidx.paging.PagingData
import com.sadri.data.repository.PeopleRepository
import com.sadri.model.PeopleEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPeopleUseCase @Inject constructor(
  private val repository: PeopleRepository
) {
  operator fun invoke(name: String): Flow<PagingData<PeopleEntity>> =
    repository.getPeople(name)
}