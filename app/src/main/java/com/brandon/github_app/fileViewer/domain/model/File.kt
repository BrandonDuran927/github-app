package com.brandon.github_app.fileViewer.domain.model

data class File(
    val content: String,
    val encoding: String,
    val name: String,
    val path: String,
    val sha: String,
    val type: String,
)
