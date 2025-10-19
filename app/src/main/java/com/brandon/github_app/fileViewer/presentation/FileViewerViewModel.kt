package com.brandon.github_app.fileViewer.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LOG_TAG
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
    private val repoId = savedStateHandle.getStateFlow("id", -1)
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
            repository.getFile(
                repoId = repoId.value,
                owner = owner.value,
                repoName = repo.value,
                filePath = path.value
            ).collect { result ->
                state = when (result) {
                    is CustomResult.Success<*> -> {
                        Log.d("FileViewerViewModel", "1 getFile: ${result.data}")
                        state.copy(
                            isLoading = false,
                            file = result.data as File?,
                            language = detectLanguage(result.data?.path)
                        )
                    }

                    is CustomResult.Failure -> {
                        Log.d("FileViewerViewModel", "2 getFile: ${result.exception.message}")
                        state.copy(
                            isLoading = false,
                            error = result.exception.message
                        )
                    }
                }
            }
        }
    }

    private fun detectLanguage(fileName: String?): String {
        return when (fileName?.substringAfterLast('.', "")?.lowercase()) {
            "kt", "kts" -> "kotlin"
            "java" -> "java"
            "js" -> "javascript"
            "jsx" -> "jsx"
            "ts" -> "typescript"
            "tsx" -> "tsx"
            "py" -> "python"
            "json" -> "json"
            "xml" -> "xml"
            "html", "htm" -> "html"
            "css" -> "css"
            "scss", "sass" -> "scss"
            "md" -> "markdown"
            "yml", "yaml" -> "yaml"
            "sh", "bash" -> "bash"
            "sql" -> "sql"
            "c" -> "c"
            "cpp", "cc", "cxx" -> "cpp"
            "cs" -> "csharp"
            "php" -> "php"
            "rb" -> "ruby"
            "swift" -> "swift"
            "go" -> "go"
            "rs" -> "rust"
            "dart" -> "dart"
            "gradle" -> "gradle"
            else -> "plaintext"
        }
    }
}