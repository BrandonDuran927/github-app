package com.brandon.github_app.repoContents.data

import android.util.Log
import com.brandon.github_app.core.CustomResult
import com.brandon.github_app.repoContents.data.mappers.toDomain
import com.brandon.github_app.repoContents.data.remote.RepoContentsApi
import com.brandon.github_app.repoContents.domain.RepoContentsRepository
import com.brandon.github_app.repoContents.domain.model.RepoContentsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class RepoContentsRepositoryImpl @Inject constructor(
    private val api: RepoContentsApi
) : RepoContentsRepository {
    override suspend fun getRepoContents(
        owner: String,
        repo: String,
        path: String
    ): Flow<CustomResult<List<RepoContentsItem>>> {
        return flow {
            try {
                Log.d("RepoContentsRepository", "getRepoContents: $owner, $repo, $path")
                val response = api.getRepoContents(
                    owner = owner,
                    repo = repo,
                    path = path
                )
                val domainModels = response.toDomain()
                emit(CustomResult.Success(domainModels))
            } catch (e: Exception) {
                emit(CustomResult.Failure(e))
            }
        }
    }
}