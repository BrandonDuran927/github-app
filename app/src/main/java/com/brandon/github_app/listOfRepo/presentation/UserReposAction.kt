package com.brandon.github_app.listOfRepo.presentation

sealed class UserReposAction {
    data class ViewUserPicture(val isView: Boolean) : UserReposAction()
}