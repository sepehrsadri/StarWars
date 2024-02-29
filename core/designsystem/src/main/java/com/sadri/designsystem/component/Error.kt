package com.sadri.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sadri.designsystem.theme.space

@Composable
fun BoxScope.Error(
  message: String,
  retry: () -> Unit
) {
  Column(
    modifier = Modifier.align(Alignment.Center),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = message,
      color = MaterialTheme.colorScheme.outline
    )
    Spacer(modifier = Modifier.height(MaterialTheme.space.sMedium))
    OutlinedButton(
      onClick = { retry.invoke() }
    ) {
      Text(
        text = "Retry",
        color = MaterialTheme.colorScheme.outline
      )
    }
  }
}