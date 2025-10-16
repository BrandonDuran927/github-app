package com.brandon.github_app.core

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.brandon.github_app.core.local.SearchDao
import com.brandon.github_app.core.local.SearchDatabase
import com.brandon.github_app.searchHistory.data.SearchHistoryRepositoryImpl
import com.brandon.github_app.searchHistory.domain.SearchHistoryRepository
import com.example.beupdated.core.network.ConnectivityObserver
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DI {
    @Singleton
    @Provides
    fun provideConnectivityObserver(
        @ApplicationContext context: Context
    ): ConnectivityObserver = ConnectivityObserver(context)

    @Singleton
    @Provides
    fun provideSearchDb(@ApplicationContext context: Context) : SearchDatabase {
        return Room.databaseBuilder<SearchDatabase>(
            context,
            "search.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideSearchDao(searchDatabase: SearchDatabase) : SearchDao {
        return searchDatabase.dao
    }

    @Singleton
    @Provides
    fun provideRepository(impl: SearchHistoryRepositoryImpl) : SearchHistoryRepository {
        return impl
    }
}