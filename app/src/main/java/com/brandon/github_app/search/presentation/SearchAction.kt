package com.brandon.github_app.search.presentation

sealed class SearchAction {
    data class OnSearchQueryChange(val query: String) : SearchAction()
    data class OnSearchEmpty(val isQueryEmpty: Boolean) : SearchAction()
    object ResetState : SearchAction()
}