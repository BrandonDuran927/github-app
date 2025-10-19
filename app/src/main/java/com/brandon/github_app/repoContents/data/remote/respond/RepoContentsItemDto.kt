package com.brandon.github_app.repoContents.data.remote.respond

data class RepoContentsItemDto(
    val id: Int,
    val name: String,
    val path: String,
    val type: String,
    val sha: String
)