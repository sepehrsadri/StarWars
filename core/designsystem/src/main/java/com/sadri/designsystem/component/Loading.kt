package com.sadri.designsystem.component

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sadri.designsystem.theme.space

@Composable
fun BoxScope.Loading() {
  CircularProgressIndicator(
    modifier = Modifier
      .size(MaterialTheme.space.xLarge)
      .align(Alignment.Center),
    color = MaterialTheme.colorScheme.primary
  )
}