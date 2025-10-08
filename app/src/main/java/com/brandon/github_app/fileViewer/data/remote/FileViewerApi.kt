package com.brandon.github_app.fileViewer.data.remote

import com.brandon.github_app.fileViewer.data.remote.respond.FileDto
import com.brandon.github_app.repoContents.data.remote.respond.RepoContentsItemDto
import retrofit2.http.GET
import retrofit2.http.Path

interface FileViewerApi {
    @GET("repos/{owner}/{repo}/contents/{path}")
    suspend fun getRepoContents(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("path") path: String = ""
    ) : FileDto

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }
}