package com.sadri.network.di

import com.sadri.network.api.DefaultPeopleService
import com.sadri.network.api.PeopleService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ServiceModule {
  @Binds
  fun bindPeopleService(
    peopleService: DefaultPeopleService
  ): PeopleService
}