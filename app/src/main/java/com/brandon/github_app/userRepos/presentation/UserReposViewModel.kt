package com.brandon.github_app.userRepos.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brandon.github_app.userRepos.domain.UserRepoRepository
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
            is UserReposAction.ViewUserPicture -> state = state.copy(showUserPictureDialog = action.isView)
        }
    }

    private fun getUserRepos() {
        state = state.copy(isLoading = true, username = username.value)

        Log.d("ViewModelNaten", "${state.isLoading}")

        viewModelScope.launch {
//            delay(1500L)

            repository.getUserRepos(username.value).collectLatest { result ->
                state = state.copy(
                    userRepos = result.getOrNull() ?: emptyList(),
                    isLoading = false
                )

                Log.d("UserReposViewModel", "getUserRepos: ${state.userRepos}")
            }
        }

        Log.d("ViewModelNaten", "${state.isLoading}")
    }
}