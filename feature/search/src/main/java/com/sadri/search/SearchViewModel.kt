package com.sadri.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadri.domain.GetPeopleUseCase
import com.sadri.model.PeopleResultEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
  private val useCase: GetPeopleUseCase
) : ViewModel() {

  private val _uiState: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState.Nothing)
  val uiState: StateFlow<SearchUiState> = _uiState

  private val _textSearch = MutableStateFlow("")
  private val textSearch: StateFlow<String> = _textSearch.asStateFlow()

  init {
    viewModelScope.launch {
      textSearch.debounce(SEARCH_DEBOUNCE).collect { query ->
        search(query)
      }
    }
  }

  fun setSearchText(it: String) {
    _textSearch.value = it
  }

  fun onRetry() {
    search(textSearch.value)
  }

  fun search(query: String) {
    if (query.isEmpty()) return
    _uiState.value = SearchUiState.Loading
    viewModelScope.launch {
      useCase.invoke(query)
        .collect { result ->
          result
            .onSuccess {
              _uiState.value = SearchUiState.Success(it)
            }
            .onFailure {
              _uiState.value = SearchUiState.Error(it)
            }
        }
    }
  }

  companion object {
    private const val SEARCH_DEBOUNCE = 1000L
  }
}

sealed interface SearchUiState {
  data class Success(val result: PeopleResultEntity) : SearchUiState

  data class Error(val error: Throwable) : SearchUiState

  data object Loading : SearchUiState

  data object Nothing : SearchUiState
}