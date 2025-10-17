package com.brandon.github_app.searchHistory.data

import android.util.Log
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.ui.text.toLowerCase
import com.brandon.github_app.core.CustomResult
import com.brandon.github_app.core.local.SearchDao
import com.brandon.github_app.core.model.Search
import com.brandon.github_app.searchHistory.data.mappers.toDomain
import com.brandon.github_app.searchHistory.data.mappers.toEntity
import com.brandon.github_app.searchHistory.domain.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchHistoryRepositoryImpl @Inject constructor(
    private val dao: SearchDao
) : SearchHistoryRepository {
    override suspend fun insertSearchHistory(query: String): Flow<CustomResult<Unit>> {
        return flow {
            try {
                val existingSearch = dao.getSearchByQuery(query.lowercase())

                if (existingSearch != null) {
                    dao.deleteSearchHistory(existingSearch)
                }

                dao.upsertSearchHistory(Search(id = 0, searchHistory = query).toEntity())
                emit(CustomResult.Success(Unit))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(CustomResult.Failure(e))
            }
        }
    }

    override fun retrieveSearchHistory(): Flow<CustomResult<List<Search>>> {
        return dao.getSearchHistory()
            .map { entities ->
                try {
                    val searches = entities.toDomain().sortedByDescending {
                        it.id
                    }
                    CustomResult.Success(searches)
                } catch (e: Exception) {
                    e.printStackTrace()
                    CustomResult.Failure(e)
                }
            }
            .catch { e ->
                e.printStackTrace()
                emit(CustomResult.Failure(e))
            }
    }

    override suspend fun deleteSearchHistory(search: Search): CustomResult<Unit> {
        return try {
            dao.deleteSearchHistory(search.toEntity())
            CustomResult.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            CustomResult.Failure(e)
        }
    }

    override suspend fun deleteAllSearchHistory(): CustomResult<Unit> {
        return try {
            dao.deleteAllSearchHistory()
            CustomResult.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            CustomResult.Failure(e)
        }
    }
}