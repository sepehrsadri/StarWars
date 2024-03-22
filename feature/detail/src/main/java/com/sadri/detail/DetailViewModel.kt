package com.sadri.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadri.detail.navigation.PEOPLE_ENTITY_ARG
import com.sadri.model.PeopleEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
  private val savedStateHandle: SavedStateHandle
) : ViewModel() {

  private val _uiState: MutableStateFlow<DetailUiState> = MutableStateFlow(DetailUiState.Loading)
  val uiState: StateFlow<DetailUiState> = _uiState

  init {
    viewModelScope.launch {
      savedStateHandle.getStateFlow<PeopleEntity?>(PEOPLE_ENTITY_ARG, null)
        .collect { peopleEntity ->
          if (peopleEntity != null) {
            _uiState.value = DetailUiState.Success(peopleEntity)
          }
        }
    }
  }

}

sealed interface DetailUiState {

  data object Loading : DetailUiState

  data class Success(val data: PeopleEntity) : DetailUiState
}