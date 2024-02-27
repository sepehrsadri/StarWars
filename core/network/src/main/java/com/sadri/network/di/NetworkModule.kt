package com.sadri.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sadri.network.Constants
import com.sadri.network.api.PeopleService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
  private const val TIME_OUT = 15L

  @Provides
  fun provideHttpLoggingInterceptor(): Interceptor {
    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
    return logging
  }

  @Provides
  @Singleton
  fun provideOkHttpBuilder(
    loggingInterceptor: Interceptor
  ): OkHttpClient {
    return OkHttpClient.Builder()
      .readTimeout(TIME_OUT, TimeUnit.SECONDS)
      .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
      .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
      .callTimeout(TIME_OUT, TimeUnit.SECONDS)
      .addInterceptor(loggingInterceptor)
      .build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(
    okHttp: OkHttpClient,
    networkJson: Json
  ): Retrofit {
    return Retrofit.Builder()
      .baseUrl(Constants.API_BASE_URL)
      .client(okHttp)
      .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
      .build()
  }

  @Provides
  @Singleton
  fun providesNetworkJson(): Json = Json {
    ignoreUnknownKeys = true
  }

  @Provides
  @Singleton
  fun providePeopleService(retrofit: Retrofit): PeopleService {
    return retrofit.create(PeopleService::class.java)
  }

}