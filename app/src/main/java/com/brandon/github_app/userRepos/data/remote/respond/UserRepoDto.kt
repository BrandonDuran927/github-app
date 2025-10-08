package com.brandon.github_app.userRepos.data.remote.respond


data class UserRepoDto(
    val id: Int,
    val name: String,
    val description: String? = null,
    val owner: OwnerDto,
    val stargazers_count: Int,
    val language: String? = null
)
