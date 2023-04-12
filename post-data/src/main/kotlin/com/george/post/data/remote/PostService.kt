package com.george.post.data.remote

import com.george.post.data.model.PostAM
import retrofit2.http.GET

interface PostService {

    @GET("posts")
    suspend fun loadPosts(): List<PostAM>
}