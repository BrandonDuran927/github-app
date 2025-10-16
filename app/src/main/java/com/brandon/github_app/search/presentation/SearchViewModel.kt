package com.brandon.github_app.search.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brandon.github_app.core.CustomResult
import com.brandon.github_app.searchHistory.domain.SearchHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchHistoryRepository
) : ViewModel() {
    var state by mutableStateOf(SearchState())
        private set

    fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.OnSearchQueryChange -> state = state.copy(query = action.query)
            is SearchAction.OnSearchEmpty -> {
                state = if (action.isQueryEmpty) {
                    state.copy(isQueryEmpty = true)
                } else {
                    state.copy(isQueryEmpty = false)
                }
            }
            SearchAction.ResetState -> state = SearchState()
            is SearchAction.StoreSearch -> {
                Log.d("SearchViewModel", "Storing search query: ${action.query}")
                state = state.copy(isLoading = true)

                viewModelScope.launch {
                    repository.insertSearchHistory(action.query).collect { result ->
                        state = when (result) {
                            is CustomResult.Success<*> -> state.copy(isLoading = false)
                            is CustomResult.Failure -> state.copy(error = result.exception.message)
                        }
                    }
                }
            }
        }
    }
}