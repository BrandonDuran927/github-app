package com.brandon.github_app.fileViewer.presentation

import android.util.Base64
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brandon.github_app.core.CustomResult
import com.brandon.github_app.fileViewer.domain.FileViewerRepository
import com.brandon.github_app.fileViewer.domain.model.File
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FileViewerViewModel @Inject constructor(
    private val repository: FileViewerRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val owner = savedStateHandle.getStateFlow("owner", "")
    private val repo = savedStateHandle.getStateFlow("repoName", "")
    private val path = savedStateHandle.getStateFlow("filePath", "")

    var state by mutableStateOf(FileViewerState())
        private set

    init {
        getFile()
    }

    fun onAction(action: FileViewerAction) {

    }

    private fun getFile() {
        state = state.copy(isLoading = true)

        viewModelScope.launch {
            repository.getFile(owner.value, repo.value, path.value).collect { result ->
                state = when (result) {
                    is CustomResult.Success<*> -> {
                        state.copy(isLoading = false, file = result.data as File?)
                    }
                    is CustomResult.Failure -> state.copy(isLoading = false, error = result.exception.message)
                }
            }
        }
    }
}