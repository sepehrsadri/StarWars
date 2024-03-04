package com.sadri.testing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sadri.search.SearchRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HiltComponentActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      SearchRoute()
    }
  }
}