package com.brandon.github_app.core.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {
    @Upsert
    suspend fun upsertSearchHistory(searchEntity: SearchEntity)

    @Query("SELECT * FROM SearchEntity WHERE LOWER(searchHistory) = :query LIMIT 1")
    suspend fun getSearchByQuery(query: String): SearchEntity?

    @Delete()
    suspend fun deleteSearchHistory(searchEntity: SearchEntity)

    @Query("DELETE FROM SearchEntity")
    suspend fun deleteAllSearchHistory()

    @Query("SELECT * FROM SearchEntity")
    fun getSearchHistory(): Flow<List<SearchEntity>>
}