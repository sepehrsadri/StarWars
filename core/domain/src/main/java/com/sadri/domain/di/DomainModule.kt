package com.sadri.domain.di

import com.sadri.domain.DefaultGetPeopleUseCase
import com.sadri.domain.GetPeopleUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

  @Binds
  fun bindGetPeopleUseCase(
    getPeopleUseCase: DefaultGetPeopleUseCase
  ): GetPeopleUseCase
}