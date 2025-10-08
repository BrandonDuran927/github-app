package com.brandon.github_app.core.route

import kotlinx.serialization.Serializable

@Serializable
object Search

@Serializable
data class UserRepos(val username: String)

@Serializable
data class RepoContents(
    val owner: String?,
    val repoName: String,
    val path: String = ""
)

@Serializable
data class FileViewer(
    val owner: String,
    val repoName: String,
    val filePath: String
)
