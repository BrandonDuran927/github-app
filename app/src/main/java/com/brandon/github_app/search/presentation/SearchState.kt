package com.brandon.github_app.search.presentation

data class SearchState(
    // TODO: Implement other state.
    val query: String = "",
    val isLoading: Boolean = false,
    val isQueryEmpty: Boolean = false,
    val error: String? = null
)
