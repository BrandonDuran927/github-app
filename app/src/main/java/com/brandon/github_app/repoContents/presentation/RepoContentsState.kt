package com.brandon.github_app.repoContents.presentation

import com.brandon.github_app.repoContents.domain.model.RepoContentsItem

data class RepoContentsState(
    val isLoading: Boolean = false,
    val ownerName: String = "",
    val repoId: Int? = null,
    val repoName: String = "",
    val repoContents: List<RepoContentsItem> = emptyList(),
    val error: String? = null
)
