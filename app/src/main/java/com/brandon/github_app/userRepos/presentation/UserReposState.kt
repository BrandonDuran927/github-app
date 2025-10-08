package com.brandon.github_app.userRepos.presentation

import com.brandon.github_app.userRepos.domain.model.UserRepo

data class UserReposState(
    val username: String = "",
    val userRepos: List<UserRepo> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val showUserPictureDialog: Boolean = false
)
