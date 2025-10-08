package com.brandon.github_app.userRepos.domain

import com.brandon.github_app.core.CustomResult
import com.brandon.github_app.userRepos.domain.model.UserRepo
import kotlinx.coroutines.flow.Flow

interface UserRepoRepository {
    suspend fun getUserRepos(username: String): Flow<CustomResult<List<UserRepo>>>
}


