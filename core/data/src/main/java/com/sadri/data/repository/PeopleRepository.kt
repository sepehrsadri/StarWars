package com.sadri.data.repository

import com.sadri.model.PeopleResultEntity
import kotlinx.coroutines.flow.Flow

interface PeopleRepository {
  fun getPeople(name: String): Flow<Result<PeopleResultEntity>>
}