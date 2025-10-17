package com.brandon.github_app.listOfRepo.data

import android.util.Log
import com.brandon.github_app.core.CustomResult
import com.brandon.github_app.listOfRepo.data.local.RepositoryDao
import com.brandon.github_app.listOfRepo.data.mappers.toDomain
import com.brandon.github_app.listOfRepo.data.remote.UserRepoListApi
import com.brandon.github_app.listOfRepo.domain.UserRepoRepository
import com.brandon.github_app.listOfRepo.domain.model.UserRepo
import com.example.beupdated.core.network.NetworkStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class UserRepoRepositoryImpl @Inject constructor(
    private val api: UserRepoListApi,
    private val dao: RepositoryDao
) : UserRepoRepository {
    override suspend fun getUserRepos(
        username: String,
        networkStatus: NetworkStatus
    ): Flow<CustomResult<List<UserRepo>>> {
        return flow {
            if(networkStatus == NetworkStatus.Available) try {
                val remoteRepos = api.getUserRepos(username)
                val localRepos = dao.getRepositories()



//                emit(CustomResult.Success(domainModels))
            } catch (e: Exception) {
                emit(CustomResult.Failure(e))
            }
        }
    }


}