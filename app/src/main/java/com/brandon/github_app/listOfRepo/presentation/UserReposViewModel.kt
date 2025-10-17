package com.brandon.github_app.listOfRepo.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brandon.github_app.listOfRepo.domain.UserRepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserReposViewModel @Inject constructor(
    private val repository: UserRepoRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var state by mutableStateOf(UserReposState())
        private set

    private val username = savedStateHandle.getStateFlow("username", "")

    init {
        getUserRepos()
    }

    fun onAction(action: UserReposAction) {
        when (action) {
            is UserReposAction.ViewUserPicture -> state = state.copy(showUserPictureDialog = action.isView,)
            is UserReposAction.NetworkStatusChanged -> state = state.copy(networkStatus = action.networkStatus)
        }
    }

    private fun getUserRepos() {
        state = state.copy(username = username.value, isLoading = true,)

        viewModelScope.launch {
            repository.getUserRepos(username.value).collectLatest { result ->
                state = state.copy(
                    userRepos = result.getOrNull() ?: emptyList(),
                )
            }
        }
    }
}