package com.brandon.github_app.repoContents.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brandon.github_app.core.CustomResult
import com.brandon.github_app.repoContents.domain.RepoContentsRepository
import com.brandon.github_app.repoContents.domain.model.RepoContentsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoContentsViewModel @Inject constructor(
    private val repository: RepoContentsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var state by mutableStateOf(value = RepoContentsState())
        private set

    private val owner = savedStateHandle.getStateFlow("owner", "")
        .also { state = state.copy(ownerName = it.value) }
    private val repo = savedStateHandle.getStateFlow("repoName", "")
        .also { state = state.copy(repoName = it.value) }
    private val repoId = savedStateHandle.getStateFlow("id", -1)
    private val path = savedStateHandle.getStateFlow("path", "")


    init {
        retrieveRepoContents()
    }

    fun onAction(action: RepoContentsAction) {

    }

    private fun retrieveRepoContents() {
        state = state.copy(isLoading = true)

        viewModelScope.launch {
            repository.getRepoContents(
                repoId = repoId.value,
                owner = owner.value,
                repo = repo.value,
                path = path.value
            )
                .collectLatest { result ->
                    when (result) {
                        is CustomResult.Success<List<RepoContentsItem>> -> {
                            Log.d("RepoContentsViewModel", "result: $result")
                            state = state.copy(
                                repoContents = result.data,
                                isLoading = false
                            )
                        }

                        is CustomResult.Failure -> {
                            Log.d("RepoContentsViewModel", "result: ${result.exception.message}")
                            state = state.copy(
                                error = result.exception.message,
                                isLoading = false
                            )
                        }
                    }
                }
        }
    }

}