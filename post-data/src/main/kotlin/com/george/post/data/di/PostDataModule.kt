package com.george.post.data.di

import com.george.post.data.PostRepositoryImpl
import com.george.post.data.PostsDS
import com.george.post.data.remote.PostApiDS
import com.george.post.data.remote.PostApiService
import com.george.posts.domain.PostRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [BindPostDataModule::class])
class PostDataModule {

    @Singleton
    @Provides
    fun provideApiService(): PostApiService = PostApiService()

}

@InstallIn(SingletonComponent::class)
@Module
interface BindPostDataModule {

    @Singleton
    @Binds
    fun bindRemoteDataSource(remoteDS: PostApiDS): PostsDS.Remote

    @Singleton
    @Binds
    fun bindRepository(repositoryImpl: PostRepositoryImpl): PostRepository
}