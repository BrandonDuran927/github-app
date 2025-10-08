package com.brandon.github_app.repoContents.data.remote

import com.brandon.github_app.repoContents.data.remote.respond.RepoContentsItemDto
import retrofit2.http.GET
import retrofit2.http.Path


interface RepoContentsApi {
    @GET("repos/{owner}/{repo}/contents/{path}")
    suspend fun getRepoContents(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("path") path: String = ""
    ) : List<RepoContentsItemDto>

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }
}