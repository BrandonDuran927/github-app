package com.brandon.github_app.listOfRepo.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface RepositoryDao {
    @Upsert
    suspend fun upsertRepository(repositoryEntity: List<RepositoryEntity>)

    @Query("SELECT * FROM RepositoryEntity")
    suspend fun getRepositories(): List<RepositoryEntity>
}