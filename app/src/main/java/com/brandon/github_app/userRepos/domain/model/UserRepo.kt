package com.brandon.github_app.userRepos.domain.model

import com.brandon.github_app.core.model.Owner

data class UserRepo(
    val id: Int,
    val name: String,
    val description: String?,
    val owner: Owner,
    val stargazers_count: Int,
    val language: String?
)