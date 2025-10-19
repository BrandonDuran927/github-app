package com.brandon.github_app.repoContents.domain.model

data class RepoContentsItem(
    val repoId: Int,
    val name: String,
    val path: String,
    val type: String,
    val sha: String
)