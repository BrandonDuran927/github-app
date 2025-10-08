package com.brandon.github_app.userRepos.data

import android.util.Log
import com.brandon.github_app.core.CustomResult
import com.brandon.github_app.userRepos.data.mappers.toDomain
import com.brandon.github_app.userRepos.data.remote.UserRepoListApi
import com.brandon.github_app.userRepos.domain.UserRepoRepository
import com.brandon.github_app.userRepos.domain.model.UserRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class UserRepoRepositoryImpl @Inject constructor(
    private val api: UserRepoListApi  // TODO: Check if this is working
) : UserRepoRepository {
    override suspend fun getUserRepos(username: String): Flow<CustomResult<List<UserRepo>>> {
        return flow {
            Log.d("UserRepoRepositoryImpl", "getUserRepos: $username")
            try {
                val response = api.getUserRepos(username)  // Return list of UserRepoDto
                val domainModels = response.toDomain()  // Convert list of UserRepoDto to list of UserRepo
                Log.d("UserRepoRepositoryImpl", "response: $response")  // TODO: Hindi na to nag t-trigger
                Log.d("UserRepoRepositoryImpl", "getUserRepos: $domainModels")
                emit(CustomResult.Success(domainModels))
            } catch (e: Exception) {
                Log.e("UserRepoRepositoryImpl", "API failed: ${e.message}", e)
                emit(CustomResult.Failure(e))
            }
        }
    }
}