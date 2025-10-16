package com.brandon.github_app.searchHistory.presentation

import com.brandon.github_app.core.model.Search


sealed class SearchHistoryAction {
    data class OnRemoveSearchHistory(val search: Search) : SearchHistoryAction()
    data object OnRemoveAllSearchHistory : SearchHistoryAction()
}