package com.george.post.data

import com.george.posts.domain.model.PostDM
import kotlinx.coroutines.flow.Flow

interface PostsDS {

    interface Remote{
        suspend fun loadPosts(): Flow<List<PostDM>>
    }
}