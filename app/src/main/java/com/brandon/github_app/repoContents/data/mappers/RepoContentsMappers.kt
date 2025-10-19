package com.brandon.github_app.repoContents.data.mappers

import com.brandon.github_app.repoContents.data.local.RepoContentEntity
import com.brandon.github_app.repoContents.data.remote.respond.RepoContentsItemDto
import com.brandon.github_app.repoContents.domain.model.RepoContentsItem

fun RepoContentsItemDto.toEntity(repoId: Int, parentPath: String): RepoContentEntity {
    return RepoContentEntity(
        id = "${repoId}_${path}",
        repositoryId = repoId,
        name = name,
        path = path,
        parentPath = parentPath,
        type = type,
        sha = sha
    )
}

fun RepoContentEntity.toDomain(): RepoContentsItem {
    return RepoContentsItem(
        repoId = id.substringBefore("_").toInt(),
        name = name,
        path = path,
        type = type,
        sha = sha
    )
}

fun List<RepoContentEntity>.toDomain(): List<RepoContentsItem> {
    return map { it.toDomain() }
}


