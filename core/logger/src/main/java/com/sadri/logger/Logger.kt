package com.sadri.logger

import timber.log.Timber

object Logger {
  fun init() {
    Timber.plant(
      if (BuildConfig.DEBUG) {
        Timber.DebugTree()
      } else {
        ProductionLogTree()
      }
    )
  }
}