package com.george.post.data.remote

import com.george.post.data.model.PostAM
import com.george.posts.domain.model.PostDM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostApiService {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .client(OkHttpClient().newBuilder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    private val service by lazy {
        retrofit.create(PostService::class.java)
    }

    suspend fun loadPosts(): Flow<List<PostDM>> = flow { emit(service.loadPosts().toPostsDM()) }
}

internal fun List<PostAM>.toPostsDM() = map { it.toPostDM() }

private fun PostAM.toPostDM() = PostDM(
    id = id,
    body = body
)

