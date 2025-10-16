package com.brandon.github_app.listOfRepo.domain

import com.brandon.github_app.core.CustomResult
import com.brandon.github_app.listOfRepo.domain.model.UserRepo
import kotlinx.coroutines.flow.Flow

interface UserRepoRepository {
    suspend fun getUserRepos(username: String): Flow<CustomResult<List<UserRepo>>>
}


