package com.sadri.starwars

import android.app.Application
import com.sadri.logger.Logger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
  override fun onCreate() {
    super.onCreate()
    Logger.init()
  }
}