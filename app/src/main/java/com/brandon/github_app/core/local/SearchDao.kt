package com.brandon.github_app.core.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {
    @Upsert
    suspend fun upsertSearch(searchEntity: SearchEntity)

    @Query("SELECT * FROM SearchEntity WHERE LOWER(searchHistory) = :query LIMIT 1")
    suspend fun getSearchByQuery(query: String): SearchEntity?

    @Query("UPDATE searchentity SET isArchive = 1 WHERE isArchive = 0")
    suspend fun archiveAllSearchHistory()

    @Query("SELECT * FROM SearchEntity")
    fun getSearchHistory(): Flow<List<SearchEntity>>

    @Delete
    suspend fun deleteSearchHistory(searchEntity: SearchEntity)

    @Query("UPDATE SearchEntity SET isArchive = 1 WHERE id = :id")
    suspend fun archiveSearchHistory(id: Int)

    @Query("SELECT * FROM SearchEntity WHERE isArchive = 0 ORDER BY id DESC")
    fun getActiveSearchHistory(): Flow<List<SearchEntity>>
}