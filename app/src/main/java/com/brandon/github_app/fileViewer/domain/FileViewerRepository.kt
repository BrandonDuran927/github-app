package com.brandon.github_app.fileViewer.domain

import com.brandon.github_app.core.CustomResult
import com.brandon.github_app.fileViewer.domain.model.File
import kotlinx.coroutines.flow.Flow

interface FileViewerRepository {
    suspend fun getFile(
        owner: String,
        repoName: String,
        filePath: String
    ) : Flow<CustomResult<File>>
}