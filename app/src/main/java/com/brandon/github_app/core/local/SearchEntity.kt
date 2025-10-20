package com.brandon.github_app.core.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val searchHistory: String,
    val isArchive: Boolean = false,
)
