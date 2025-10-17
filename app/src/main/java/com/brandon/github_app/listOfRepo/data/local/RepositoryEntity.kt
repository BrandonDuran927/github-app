package com.brandon.github_app.listOfRepo.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.brandon.github_app.core.model.Owner

@Entity
data class RepositoryEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String? = null,
    @Embedded(prefix = "owner_") val owner: Owner,  //Flattens Owner fields
    val stargazers_count: Int,
    val language: String? = null,
    val pushed_at: String,
    val updated_at: String
)