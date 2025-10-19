package com.brandon.github_app.fileViewer.data

import android.util.Base64
import android.util.Log
import com.brandon.github_app.core.CustomResult
import com.brandon.github_app.fileViewer.data.local.FileViewerDao
import com.brandon.github_app.fileViewer.data.local.FileViewerEntity
import com.brandon.github_app.fileViewer.data.mappers.toDomain
import com.brandon.github_app.fileViewer.data.mappers.toEntity
import com.brandon.github_app.fileViewer.data.remote.FileViewerApi
import com.brandon.github_app.fileViewer.data.remote.respond.FileDto
import com.brandon.github_app.fileViewer.domain.FileViewerRepository
import com.brandon.github_app.fileViewer.domain.model.File
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FileViewerRepositoryImpl @Inject constructor(
    private val api: FileViewerApi,
    private val dao: FileViewerDao
) : FileViewerRepository {
    override suspend fun getFile(
        repoId: Int,
        owner: String,
        repoName: String,
        filePath: String
    ): Flow<CustomResult<File>> {
        return flow {
            try {
                val id = "${repoId}_${filePath}"

                val localFile = dao.getFile(repoId = id, filePath = filePath)

                if (localFile != null) {
                    val file = localFile.toDomain().copy(content = decodeBase64Content(localFile.content))
                    emit(CustomResult.Success(file))
                }

                val remoteFile = api.getRepoContents(
                    owner = owner,
                    repo = repoName,
                    path = filePath
                )

                val hasChanges = localFile == null || detectChanges(localFile, remoteFile)

                if (hasChanges) {
                    dao.upsertFile(remoteFile.toEntity(repoId))
                    val updatedFile = dao.getFile(repoId = id, filePath = filePath)
                    Log.d("FileViewerRepositoryImpl", "repoId: $repoId, filePath: $filePath")
                    val file = updatedFile!!.toDomain().copy(content = decodeBase64Content(updatedFile.content))
                    emit(CustomResult.Success(file))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Try to return cached file on error
                val localFile = dao.getFile(repoId = "${repoId}_${filePath}", filePath = filePath)
                if (localFile != null) {
                    val file = localFile.toDomain().copy(content = decodeBase64Content(localFile.content))
                    emit(CustomResult.Success(file))
                } else {
                    emit(CustomResult.Failure(e))
                }
            }
        }
    }

    private fun detectChanges(
        localFile: FileViewerEntity,
        remoteFile: FileDto
    ) : Boolean {
        return localFile.sha != remoteFile.sha
    }

    private fun decodeBase64Content(base64Content: String?): String {
        if (base64Content == null) return ""

        return try {
            val cleanedContent = base64Content.replace("\n", "")
            val decodedBytes = Base64.decode(cleanedContent, Base64.DEFAULT)
            String(decodedBytes, Charsets.UTF_8)
        } catch (e: Exception) {
            "Error decoding file content"
        }
    }
}