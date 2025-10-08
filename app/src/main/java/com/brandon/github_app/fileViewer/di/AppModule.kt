package com.brandon.github_app.fileViewer.di

import com.brandon.github_app.fileViewer.data.remote.FileViewerApi
import com.brandon.github_app.repoContents.data.remote.RepoContentsApi
import com.brandon.github_app.userRepos.data.remote.UserRepoListApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun providesUserRepoApi() : FileViewerApi {
        return Retrofit.Builder()
            .baseUrl(FileViewerApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(FileViewerApi::class.java)

    }
}