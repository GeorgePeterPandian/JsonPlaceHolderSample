package com.george.post.data

import com.george.post.data.remote.PostApiDS
import com.george.post.data.remote.PostApiService
import com.george.posts.domain.model.PostDM
import io.mockk.coEvery
import io.mockk.spyk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class PostApiDSTest {

    //Step 1: Mock the dependencies
    private val apiService: PostApiService = spyk()

    //Step 2: Mock the dependencies result
    private val postsDM = listOf(PostDM(id = 1, body = "Test"))

    //Step 3: Late init class in test
    private lateinit var apiDS: PostApiDS

    @Before
    fun `begin each test case with`() {
        apiDS = PostApiDS(apiService)
    }

    @Test
    fun `emit data from the api service`() = runTest {
        //Given
        coEvery {
            apiService.loadPosts()
        } returns flow { emit(postsDM) }

        //When
        val result = apiDS.loadPosts().last()

        //Then
        assertEquals(postsDM.size, result.size)
        assertEquals(postsDM.firstOrNull()?.id, result.firstOrNull()?.id)
        assertEquals(postsDM.firstOrNull()?.body, result.firstOrNull()?.body)
    }
}