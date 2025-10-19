package com.brandon.github_app.fileViewer.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface FileViewerDao {
    @Upsert
    suspend fun upsertFile(file: FileViewerEntity)

    @Query("DELETE FROM FileViewerEntity WHERE id = :id")
    suspend fun deleteFile(id: String)

    @Query("SELECT * FROM FileViewerEntity WHERE id = :repoId AND path = :filePath")
    suspend fun getFile(repoId: String, filePath: String): FileViewerEntity?
}