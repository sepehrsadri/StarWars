package com.sadri.starwars.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import com.sadri.starwars.navigation.NavHost

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun App(
  windowSizeClass: WindowSizeClass,
  appState: AppState = rememberAppState(
    windowSizeClass = windowSizeClass
  ),
) {
  Scaffold(
    modifier = Modifier.semantics {
      testTagsAsResourceId = true
    },
    containerColor = Color.Transparent,
    contentWindowInsets = WindowInsets(0, 0, 0, 0),
  ) { padding ->
    Row(
      Modifier
        .fillMaxSize()
        .padding(padding)
        .consumeWindowInsets(padding)
        .windowInsetsPadding(
          WindowInsets.safeDrawing.only(
            WindowInsetsSides.Horizontal,
          ),
        ),
    ) {

      Column(Modifier.fillMaxSize()) {
        NavHost(appState = appState)
      }
    }
  }
}
