package com.brandon.github_app.repoContents.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface RepoContentsDao {
    @Upsert
    suspend fun upsertRepoContents(repoContent: List<RepoContentEntity>)

    // Single query - use parentPath instead of path
    @Query("SELECT * FROM RepoContentEntity WHERE repositoryId = :repoId AND parentPath = :parentPath")
    suspend fun getRepoContents(repoId: Int, parentPath: String): List<RepoContentEntity>

    @Query("DELETE FROM RepoContentEntity WHERE id IN (:ids)")
    suspend fun deleteRepoContents(ids: List<String>)

    @Query("DELETE FROM RepoContentEntity")
    suspend fun deleteAllRepoContents()
}
