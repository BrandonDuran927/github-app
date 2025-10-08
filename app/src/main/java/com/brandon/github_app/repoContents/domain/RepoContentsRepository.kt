package com.brandon.github_app.repoContents.domain

import com.brandon.github_app.core.CustomResult
import com.brandon.github_app.repoContents.domain.model.RepoContentsItem
import kotlinx.coroutines.flow.Flow

interface RepoContentsRepository {
    suspend fun getRepoContents(owner: String, repo: String, path: String): Flow<CustomResult<List<RepoContentsItem>>>
}