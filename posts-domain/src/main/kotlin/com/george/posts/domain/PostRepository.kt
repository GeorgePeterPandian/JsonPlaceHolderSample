package com.george.posts.domain

import com.george.posts.domain.model.PostDM
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    suspend fun loadPosts(): Flow<List<PostDM>>
}