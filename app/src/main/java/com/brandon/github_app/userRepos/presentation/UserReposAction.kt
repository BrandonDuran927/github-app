package com.brandon.github_app.userRepos.presentation

sealed class UserReposAction {
    data class ViewUserPicture(val isView: Boolean) : UserReposAction()
}