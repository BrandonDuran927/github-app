package com.brandon.github_app.listOfRepo.di

import com.brandon.github_app.listOfRepo.data.UserRepoRepositoryImpl
import com.brandon.github_app.listOfRepo.domain.UserRepoRepository
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
        impl: UserRepoRepositoryImpl
    ): UserRepoRepository
}