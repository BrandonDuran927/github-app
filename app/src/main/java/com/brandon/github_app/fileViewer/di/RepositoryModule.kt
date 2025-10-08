package com.brandon.github_app.fileViewer.di

import com.brandon.github_app.fileViewer.data.FileViewerRepositoryImpl
import com.brandon.github_app.fileViewer.domain.FileViewerRepository
import com.brandon.github_app.repoContents.data.RepoContentsRepositoryImpl
import com.brandon.github_app.repoContents.domain.RepoContentsRepository
import com.brandon.github_app.userRepos.data.UserRepoRepositoryImpl
import com.brandon.github_app.userRepos.domain.UserRepoRepository
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