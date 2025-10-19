package com.brandon.github_app.repoContents.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RepoContentEntity(
    @PrimaryKey val id: String, // "123_README.md" or "456_src/main.kt"
    val repositoryId: Int,
    val name: String,
    val path: String,
    val parentPath: String,
    val type: String,
    val sha: String
)
