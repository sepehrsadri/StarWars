package com.sadri.domain

import androidx.paging.PagingData
import com.sadri.data.repository.PeopleRepository
import com.sadri.model.PeopleEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultGetPeopleUseCase @Inject constructor(
  private val repository: PeopleRepository
) : GetPeopleUseCase {
  override operator fun invoke(name: String): Flow<PagingData<PeopleEntity>> =
    repository.getPeople(name)
}