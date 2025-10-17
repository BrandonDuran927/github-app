package com.brandon.github_app.listOfRepo.domain.model

import com.brandon.github_app.core.model.Owner

data class UserRepo(
    val id: Int,
    val name: String,
    val description: String?,
    val owner: Owner,
    val stargazers_count: Int,
    val language: String?,
    val pushed_at: String,
    val updated_at: String
)