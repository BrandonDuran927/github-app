package com.brandon.github_app.searchHistory.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brandon.github_app.core.CustomResult
import com.brandon.github_app.core.model.Search
import com.brandon.github_app.searchHistory.domain.SearchHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchHistoryViewModel @Inject constructor(
    private val repository: SearchHistoryRepository
) : ViewModel() {
    var state by mutableStateOf(SearchHistoryState())
        private set

    init {
        retrieveAllHistory()
    }

    fun onAction(action: SearchHistoryAction) {
        when (action) {
            is SearchHistoryAction.OnRemoveSearchHistory -> {
                viewModelScope.launch {
                    val result = repository.archiveSearchHistory(action.search)

                    when (result) {
                        is CustomResult.Success<*> -> { /* no-op */ }
                        is CustomResult.Failure -> state = state.copy(error = result.exception.message)
                    }
                }
            }
            SearchHistoryAction.OnRemoveAllSearchHistory -> {
                viewModelScope.launch {
                    val result = repository.archiveAllSearchHistory()

                    when (result) {
                        is CustomResult.Success<*> -> { /* no-op */ }
                        is CustomResult.Failure -> state = state.copy(error = result.exception.message)
                    }
                }
            }
        }
    }

    private fun retrieveAllHistory() {
        state = state.copy(isLoading = true)

        viewModelScope.launch {
            repository.retrieveSearchHistory().collect { result ->

                state = when (result) {
                    is CustomResult.Success<List<Search>> -> {
                        state.copy(
                            searchHistory = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is CustomResult.Failure -> state.copy(error = result.exception.message)
                }

            }

        }
    }
}