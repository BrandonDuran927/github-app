package com.brandon.github_app.listOfRepo.data.remote

import com.brandon.github_app.listOfRepo.data.remote.respond.UserRepoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface UserRepoListApi {
    @GET("/users/{username}/repos")
    suspend fun getUserRepos(
        @Path("username") username: String
    ) : List<UserRepoDto>

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }
}

