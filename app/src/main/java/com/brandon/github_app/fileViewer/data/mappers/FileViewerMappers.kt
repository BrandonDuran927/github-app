package com.brandon.github_app.fileViewer.data.mappers

import com.brandon.github_app.fileViewer.data.local.FileViewerEntity
import com.brandon.github_app.fileViewer.data.remote.respond.FileDto
import com.brandon.github_app.fileViewer.domain.model.File

fun FileDto.toEntity(repoId: Int) : FileViewerEntity {
    return FileViewerEntity(
        id = "${repoId}_${path}",
        repositoryId = repoId,
        content = content,
        encoding = encoding,
        name = name,
        path = path,
        sha = sha,
        type = type
    )
}

fun FileViewerEntity.toDomain(): File {
    return File(
        content = content,
        encoding = encoding,
        name = name,
        path = path,
        sha = sha,
        type = type
    )
}