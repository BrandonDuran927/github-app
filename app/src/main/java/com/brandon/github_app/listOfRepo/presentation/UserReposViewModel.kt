package com.brandon.github_app.listOfRepo.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brandon.github_app.listOfRepo.domain.UserRepoRepository
import com.example.beupdated.core.network.ConnectivityObserver
import com.example.beupdated.core.network.NetworkStatus
import com.example.beupdated.core.network.NetworkViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserReposViewModel @Inject constructor(
    private val repository: UserRepoRepository,
    private val connectivityObserver: ConnectivityObserver,
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
        }
    }

    private fun getUserRepos() {
        state = state.copy(username = username.value, isLoading = true,)

        viewModelScope.launch {
            repository.getUserRepos(username.value).collectLatest { result ->

                state = state.copy(
                    userRepos = result.getOrNull() ?: emptyList(),
                    isLoading = false
                )
            }
        }
    }
}