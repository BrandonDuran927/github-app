package com.brandon.github_app.fileViewer.di

import android.content.Context
import androidx.room.Room
import com.brandon.github_app.fileViewer.data.remote.FileViewerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.brandon.github_app.BuildConfig
import com.brandon.github_app.fileViewer.data.local.FileViewerDao
import com.brandon.github_app.fileViewer.data.local.FileViewerDatabase
import dagger.hilt.android.qualifiers.ApplicationContext


@Module
@InstallIn(SingletonComponent::class)
object FileViewerModule {

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
    fun providesFileViewerRepoApi(): FileViewerApi {
        return Retrofit.Builder()
            .baseUrl(FileViewerApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(FileViewerApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFileViewerDatabase(@ApplicationContext context: Context) : FileViewerDatabase {
        return Room.databaseBuilder(
            context,
            FileViewerDatabase::class.java,
            "fileViewer_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFileViewerDao(db: FileViewerDatabase) : FileViewerDao {
        return db.dao
    }
 }