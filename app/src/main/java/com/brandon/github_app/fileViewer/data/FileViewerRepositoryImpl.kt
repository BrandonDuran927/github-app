package com.brandon.github_app.fileViewer.data

import android.util.Base64
import android.util.Log
import com.brandon.github_app.core.CustomResult
import com.brandon.github_app.fileViewer.data.mappers.toDomain
import com.brandon.github_app.fileViewer.data.remote.FileViewerApi
import com.brandon.github_app.fileViewer.domain.FileViewerRepository
import com.brandon.github_app.fileViewer.domain.model.File
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FileViewerRepositoryImpl @Inject constructor(
    private val api: FileViewerApi
) : FileViewerRepository {
    override suspend fun getFile(
        owner: String,
        repoName: String,
        filePath: String
    ): Flow<CustomResult<File>> {
        return flow {
            try {
                val response = api.getRepoContents(
                    owner = owner,
                    repo = repoName,
                    path = filePath
                )

                // Decode instantly
                val file = response.toDomain().copy(content = decodeBase64Content(response.content))

                Log.d("FileViewerRepositoryImpl", "getFile: $file")

                emit(CustomResult.Success(file))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(CustomResult.Failure(e))
            }
        }
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