package com.brandon.github_app.search.presentation

import com.brandon.github_app.core.model.Search

sealed class SearchAction {
    data class OnSearchQueryChange(val query: String) : SearchAction()
    data class OnSearchEmpty(val isQueryEmpty: Boolean) : SearchAction()
    data object ResetState : SearchAction()
    data class StoreSearch(val query: String) : SearchAction()
}