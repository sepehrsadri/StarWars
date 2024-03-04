package com.sadri.domain

import androidx.paging.PagingData
import com.sadri.model.PeopleEntity
import kotlinx.coroutines.flow.Flow

interface GetPeopleUseCase {
  operator fun invoke(name: String): Flow<PagingData<PeopleEntity>>
}