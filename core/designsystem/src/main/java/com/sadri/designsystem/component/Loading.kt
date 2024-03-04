package com.sadri.designsystem.component

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.sadri.designsystem.R
import com.sadri.designsystem.theme.space

@Composable
fun BoxScope.Loading() {
  CircularProgressIndicator(
    modifier = Modifier
      .testTag(stringResource(id = R.string.progress_bar_test_tag))
      .size(MaterialTheme.space.xLarge)
      .align(Alignment.Center),
    color = MaterialTheme.colorScheme.primary
  )
}