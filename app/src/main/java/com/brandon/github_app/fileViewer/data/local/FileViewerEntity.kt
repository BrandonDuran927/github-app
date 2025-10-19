package com.brandon.github_app.fileViewer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.reflect.KClass

@Entity
data class FileViewerEntity(
    @PrimaryKey val id: String,
    val repositoryId: Int,
    val content: String,
    val encoding: String,
    val name: String,
    val path: String,
    val sha: String,
    val type: String,
)
