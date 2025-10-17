package com.brandon.github_app.listOfRepo.presentation

import com.brandon.github_app.listOfRepo.domain.model.UserRepo
import com.example.beupdated.core.network.NetworkStatus

data class UserReposState(
    val username: String = "",
    val userRepos: List<UserRepo> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val showUserPictureDialog: Boolean = false,
    val networkStatus: NetworkStatus = NetworkStatus.Unavailable
)
