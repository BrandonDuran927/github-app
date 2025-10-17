package com.brandon.github_app.listOfRepo.di

import android.content.Context
import androidx.room.Room
import com.brandon.github_app.BuildConfig
import com.brandon.github_app.core.local.SearchDatabase
import com.brandon.github_app.listOfRepo.data.local.RepositoryDao
import com.brandon.github_app.listOfRepo.data.local.RepositoryDatabase
import com.brandon.github_app.listOfRepo.data.remote.UserRepoListApi
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
object ListOfRepoModule {

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
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun providesUserRepoApi() : UserRepoListApi {
        return Retrofit.Builder()
            .baseUrl(UserRepoListApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(UserRepoListApi::class.java)

    }

    @Singleton
    @Provides
    fun provideRepositoryDb(@ApplicationContext context: Context) : RepositoryDatabase {
        return Room.databaseBuilder<RepositoryDatabase>(
            context,
            "repository.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideRepositoryDao(repositoryDatabase: RepositoryDatabase) : RepositoryDao {
        return repositoryDatabase.dao
    }


}