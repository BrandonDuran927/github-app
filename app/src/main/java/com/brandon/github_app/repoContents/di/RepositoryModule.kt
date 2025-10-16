package com.brandon.github_app.repoContents.di

import com.brandon.github_app.repoContents.data.RepoContentsRepositoryImpl
import com.brandon.github_app.repoContents.domain.RepoContentsRepository
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
        impl: RepoContentsRepositoryImpl
    ): RepoContentsRepository
}