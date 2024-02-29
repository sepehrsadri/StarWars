package com.sadri.data.repository

import androidx.paging.PagingData
import com.sadri.model.PeopleEntity
import kotlinx.coroutines.flow.Flow

interface PeopleRepository {
  fun getPeople(name: String): Flow<PagingData<PeopleEntity>>
}