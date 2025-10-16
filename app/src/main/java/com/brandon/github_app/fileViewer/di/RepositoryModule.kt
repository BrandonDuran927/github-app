package com.brandon.github_app.fileViewer.di

import com.brandon.github_app.fileViewer.data.FileViewerRepositoryImpl
import com.brandon.github_app.fileViewer.domain.FileViewerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindUserRepoRepository(
        impl: FileViewerRepositoryImpl
    ): FileViewerRepository
}