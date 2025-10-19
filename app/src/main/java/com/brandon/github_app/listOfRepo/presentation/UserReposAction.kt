package com.brandon.github_app.listOfRepo.presentation

import com.example.beupdated.core.network.NetworkStatus

sealed class UserReposAction {
    data class ViewUserPicture(val isView: Boolean) : UserReposAction()
}