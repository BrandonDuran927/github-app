package com.brandon.github_app.repoContents.data

import android.util.Log
import com.brandon.github_app.core.CustomResult
import com.brandon.github_app.repoContents.data.local.RepoContentEntity
import com.brandon.github_app.repoContents.data.local.RepoContentsDao
import com.brandon.github_app.repoContents.data.mappers.toDomain
import com.brandon.github_app.repoContents.data.mappers.toEntity
import com.brandon.github_app.repoContents.data.remote.RepoContentsApi
import com.brandon.github_app.repoContents.data.remote.respond.RepoContentsItemDto
import com.brandon.github_app.repoContents.domain.RepoContentsRepository
import com.brandon.github_app.repoContents.domain.model.RepoContentsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.collections.forEach


class RepoContentsRepositoryImpl @Inject constructor(
    private val api: RepoContentsApi,
    private val dao: RepoContentsDao
) : RepoContentsRepository {
    override suspend fun getRepoContents(
        repoId: Int,
        owner: String,
        repo: String,
        path: String
    ): Flow<CustomResult<List<RepoContentsItem>>> {
        return flow {
            try {
                val localRepos = dao.getRepoContents(repoId, path)

                if (localRepos.isNotEmpty()) {
                    emit(CustomResult.Success(localRepos.toDomain()))
                }

                val remoteRepos = api.getRepoContents(
                    owner = owner,
                    repo = repo,
                    path = path
                )

                val changes = detectChanges(localRepos, remoteRepos, repoId, path)

                if (changes.hasChanges || localRepos.isEmpty()) {
                    if (changes.hasChanges) {
                        dao.upsertRepoContents(changes.toUpdate)
                        dao.deleteRepoContents(changes.toDelete)
                    }
                    emit(CustomResult.Success(dao.getRepoContents(repoId, path).toDomain()))
                }
            } catch (e: Exception) {
                val localRepos = dao.getRepoContents(repoId, path)
                if (localRepos.isNotEmpty()) {
                    emit(CustomResult.Success(localRepos.toDomain()))
                } else {
                    emit(CustomResult.Failure(e))
                }
            }
        }
    }

    private fun detectChanges(
        localRepos: List<RepoContentEntity>,
        remoteRepos: List<RepoContentsItemDto>,
        repoId: Int,
        parentPath: String
    ): RepositoryChanges {
        val localMap = localRepos.associateBy { it.path }
        val toUpdate = mutableListOf<RepoContentEntity>()
        val toDelete = mutableSetOf<String>()

        val remotePaths = remoteRepos.map { it.path }.toSet()

        remoteRepos.forEach { remoteRepo ->
            val localRepo = localMap[remoteRepo.path]

            if (localRepo == null || localRepo.sha != remoteRepo.sha) {
                toUpdate.add(remoteRepo.toEntity(repoId, parentPath))
            }
        }

        localRepos.forEach { localRepo ->
            if (localRepo.path !in remotePaths) {
                toDelete.add(localRepo.id)
            }
        }

        return RepositoryChanges(
            toUpdate = toUpdate,
            toDelete = toDelete.toList(),
            hasChanges = toUpdate.isNotEmpty() || toDelete.isNotEmpty()
        )
    }
}

data class RepositoryChanges(
    val toUpdate: List<RepoContentEntity>,
    val toDelete: List<String>,
    val hasChanges: Boolean
)