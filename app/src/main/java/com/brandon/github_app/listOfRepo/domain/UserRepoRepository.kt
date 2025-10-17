package com.brandon.github_app.listOfRepo.domain

import com.brandon.github_app.core.CustomResult
import com.brandon.github_app.listOfRepo.domain.model.UserRepo
import com.example.beupdated.core.network.NetworkStatus
import kotlinx.coroutines.flow.Flow

interface UserRepoRepository {
    suspend fun getUserRepos(
        username: String,
        networkStatus: NetworkStatus
    ): Flow<CustomResult<List<UserRepo>>>
}


