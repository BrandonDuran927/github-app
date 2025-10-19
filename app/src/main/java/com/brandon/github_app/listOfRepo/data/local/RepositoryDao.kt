package com.brandon.github_app.listOfRepo.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface RepositoryDao {
    @Upsert
    suspend fun upsertRepository(repositoryEntity: List<RepositoryEntity>)

    @Query("DELETE FROM RepositoryEntity WHERE id IN (:ids)")
    suspend fun deleteRepositories(ids: List<Int>)

    @Query("SELECT * FROM RepositoryEntity WHERE LOWER(owner_username) = LOWER(:username)")
    suspend fun getRepositories(username: String): List<RepositoryEntity>
}