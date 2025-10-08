package com.brandon.github_app.fileViewer.data.mappers

import com.brandon.github_app.fileViewer.data.remote.respond.FileDto
import com.brandon.github_app.fileViewer.domain.model.File

fun FileDto.toDomain(): File {
    return File(
        content = content,
        encoding = encoding,
        name = name,
        path = path,
        sha = sha,
        type = type
    )
}