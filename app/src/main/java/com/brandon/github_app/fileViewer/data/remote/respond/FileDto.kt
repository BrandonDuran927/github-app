package com.brandon.github_app.fileViewer.data.remote.respond

data class FileDto(
    val content: String,
    val encoding: String,
    val name: String,
    val path: String,
    val sha: String,
    val type: String,
)