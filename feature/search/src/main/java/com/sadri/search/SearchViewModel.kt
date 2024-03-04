package com.sadri.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.sadri.domain.GetPeopleUseCase
import com.sadri.model.PeopleEntity
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

  private val _uiState: MutableStateFlow<PagingData<PeopleEntity>> =
    MutableStateFlow(PagingData.empty())
  val uiState: StateFlow<PagingData<PeopleEntity>> = _uiState

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
    viewModelScope.launch {
      useCase.invoke(query)
        .collect { data ->
          _uiState.value = data
        }
    }
  }

  companion object {
    private const val SEARCH_DEBOUNCE = 1000L
  }
}