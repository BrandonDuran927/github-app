package com.brandon.github_app.searchHistory.domain

import com.brandon.github_app.core.CustomResult
import com.brandon.github_app.core.model.Search
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    suspend fun insertSearchHistory(query: String): Flow<CustomResult<Unit>>
    fun retrieveSearchHistory(): Flow<CustomResult<List<Search>>>
    suspend fun archiveSearchHistory(search: Search): CustomResult<Unit>
    suspend fun archiveAllSearchHistory(): CustomResult<Unit>
}