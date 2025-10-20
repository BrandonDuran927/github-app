package com.brandon.github_app.listOfRepo.data

import android.util.Log
import com.brandon.github_app.core.CustomResult
import com.brandon.github_app.listOfRepo.data.local.RepositoryDao
import com.brandon.github_app.listOfRepo.data.local.RepositoryEntity
import com.brandon.github_app.listOfRepo.data.mappers.toDomain
import com.brandon.github_app.listOfRepo.data.mappers.toEntity
import com.brandon.github_app.listOfRepo.data.remote.UserRepoListApi
import com.brandon.github_app.listOfRepo.data.remote.respond.UserRepoDto
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
        username: String
    ): Flow<CustomResult<List<UserRepo>>> {
        return flow {
            try {
                val localRepos = dao.getRepositories(username)

                if (localRepos.isNotEmpty()) {
                    emit(CustomResult.Success(localRepos.toDomain()))
                }

                val remoteRepos = api.getUserRepos(username)
                val changes = detectChanges(localRepos, remoteRepos)

                if (changes.hasChanges) {
                    dao.upsertRepository(changes.toUpdate)
                    dao.deleteRepositories(changes.toDelete)
                }
                emit(CustomResult.Success(dao.getRepositories(username).toDomain()))
            } catch (e: Exception) {
                // If remote fetch fails, check if we have local data
                val localRepos = dao.getRepositories(username)
                if (localRepos.isNotEmpty()) {
                    emit(CustomResult.Success(localRepos.toDomain()))
                } else {
                    emit(CustomResult.Failure(e))
                }
            }
        }
    }


    private fun detectChanges(
        localRepos: List<RepositoryEntity>,
        remoteRepos: List<UserRepoDto>
    ): RepositoryChanges {
        val localMap = localRepos.associateBy { it.id }
        val toUpdate = mutableListOf<RepositoryEntity>()
        val toDelete = localMap.keys.toMutableSet()

        remoteRepos.forEach { remoteRepo ->
            toDelete.remove(remoteRepo.id)

            val localRepo = localMap[remoteRepo.id]

            if (localRepo == null ||
                localRepo.pushed_at != remoteRepo.pushed_at ||
                localRepo.updated_at != remoteRepo.updated_at
            ) {
                toUpdate.add(remoteRepo.toEntity())
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
    val toUpdate: List<RepositoryEntity>,
    val toDelete: List<Int>,
    val hasChanges: Boolean
)