package com.brandon.github_app.fileViewer.presentation

import com.brandon.github_app.fileViewer.domain.model.File

data class FileViewerState(
    val isLoading: Boolean = false,
    val file: File? = null,
    val decodedContent: String? = null,
    val language: String = "",
    val error: String? = null
)
