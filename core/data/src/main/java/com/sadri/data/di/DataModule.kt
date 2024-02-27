package com.sadri.data.di

import com.sadri.data.repository.DefaultPeopleRepository
import com.sadri.data.repository.PeopleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

  @Binds
  fun bindPeopleRepository(
    peopleRepository: DefaultPeopleRepository
  ): PeopleRepository
}