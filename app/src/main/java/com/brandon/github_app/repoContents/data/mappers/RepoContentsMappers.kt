package com.brandon.github_app.repoContents.data.mappers

import com.brandon.github_app.repoContents.data.remote.respond.RepoContentsItemDto
import com.brandon.github_app.repoContents.domain.model.RepoContentsItem

fun RepoContentsItemDto.toDomain(): RepoContentsItem {
    return RepoContentsItem(
        name = name,
        path = path,
        type = type
    )
}

fun List<RepoContentsItemDto>.toDomain(): List<RepoContentsItem> {
    return map { it.toDomain() }
}

