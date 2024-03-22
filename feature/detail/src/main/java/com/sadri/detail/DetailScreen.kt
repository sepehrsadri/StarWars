package com.sadri.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadri.designsystem.component.Loading
import com.sadri.designsystem.theme.space


@Composable
fun DetailRoute(
  modifier: Modifier = Modifier,
  viewModel: DetailViewModel = hiltViewModel()
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  DetailScreen(
    modifier = modifier,
    uiState = uiState
  )
}

@Composable
private fun DetailScreen(
  modifier: Modifier = Modifier,
  uiState: DetailUiState
) {
  Box(
    modifier = modifier
      .fillMaxSize()
      .padding(MaterialTheme.space.medium)
  ) {
    when (uiState) {
      DetailUiState.Loading -> {
        Loading()
      }
      is DetailUiState.Success -> {
        val peopleEntity = uiState.data
        Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.space.small)) {
          Text(text = peopleEntity.name)
          Text(text = peopleEntity.height)
          Text(text = peopleEntity.birthYear)
        }
      }
    }
  }
}