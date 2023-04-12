package com.george.post.data

import com.george.posts.domain.PostRepository
import com.george.posts.domain.model.PostDM
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val remote: PostsDS.Remote): PostRepository {

    override suspend fun loadPosts(): Flow<List<PostDM>> = remote.loadPosts()
}