package com.george.post.data.remote

import com.george.post.data.PostsDS
import com.george.posts.domain.model.PostDM
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostApiDS @Inject constructor(private val service: PostApiService) : PostsDS.Remote {

    override suspend fun loadPosts(): Flow<List<PostDM>> = service.loadPosts()
}