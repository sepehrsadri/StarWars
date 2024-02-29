package com.sadri.starwars

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.sadri.designsystem.theme.StarWarsTheme
import com.sadri.starwars.ui.App
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      StarWarsTheme {
        App(windowSizeClass = calculateWindowSizeClass(this))
      }
    }
  }
}