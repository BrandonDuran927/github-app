package com.brandon.github_app.searchHistory.presentation

import com.brandon.github_app.core.model.Search

data class SearchHistoryState(
    val searchHistory: List<Search> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
