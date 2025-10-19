package com.brandon.github_app.repoContents.di

import android.content.Context
import androidx.room.Room
import com.brandon.github_app.BuildConfig
import com.brandon.github_app.listOfRepo.data.local.RepositoryDao
import com.brandon.github_app.repoContents.data.local.RepoContentsDao
import com.brandon.github_app.repoContents.data.local.RepoContentsDatabase
import com.brandon.github_app.repoContents.data.remote.RepoContentsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepoContentsModule {

    private val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val authInterceptor: Interceptor = Interceptor { chain ->
        val originalRequest = chain.request()

        val newRequest = if (BuildConfig.API_KEY.isNotEmpty()) {
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer ${BuildConfig.API_KEY}")
                .addHeader("Accept", "application/vnd.github+json")
                .addHeader("X-GitHub-Api-Version", "2022-11-28")
                .build()
        } else {
            originalRequest.newBuilder()
                .addHeader("Accept", "application/vnd.github+json")
                .addHeader("X-GitHub-Api-Version", "2022-11-28")
                .build()
        }

        chain.proceed(newRequest)
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .build()

    @Provides
    @Singleton
    fun providesRepoContentsApi() : RepoContentsApi {
        return Retrofit.Builder()
            .baseUrl(RepoContentsApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(RepoContentsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepoContentsDb(@ApplicationContext context: Context): RepoContentsDatabase {
        return Room.databaseBuilder<RepoContentsDatabase>(
            context,
            "repo_contents.db"
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    @Singleton
    fun provideRepoContentsDao(repoContentDb: RepoContentsDatabase) : RepoContentsDao {
        return repoContentDb.dao
    }
}