package com.brandon.github_app.search.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {
    var state by mutableStateOf(SearchState())
        private set

    fun onAction(action: SearchAction) {
        state = when(action) {
            is SearchAction.OnSearchQueryChange -> state.copy(query = action.query)
            is SearchAction.OnSearchEmpty -> {
                if (action.isQueryEmpty) {
                    state.copy(isQueryEmpty = true)
                } else {
                    state.copy(isQueryEmpty = false)
                }
            }
            SearchAction.ResetState -> SearchState()
        }
    }
}