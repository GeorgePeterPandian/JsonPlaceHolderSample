package com.george.post.data

import com.george.posts.domain.model.PostDM
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class PostRepositoryImplTest {

    //Step 1: Mock the dependencies
    private val remote: PostsDS.Remote = mockk()

    //Step 2: Mock dependencies result
    private val remoteResults = listOf(PostDM(id = 1, body = "test"))
    private val localResults = listOf(PostDM(id = 1, body = "test"))

    //Step 3: Late init the class under test
    private lateinit var repository: PostRepositoryImpl

    @Before
    fun `begin each test case with`() {
        repository = PostRepositoryImpl(remote)
    }

    @Test
    fun `emit data from the remote data source`() = runTest {
        //Given
        coEvery {
            remote.loadPosts()
        } returns flow { emit(remoteResults) }

        //When
        val result = repository.loadPosts().last()

        //Then
        assertEquals(remoteResults.size, result.size)
        assertEquals(remoteResults.firstOrNull()?.id, result.firstOrNull()?.id)
        assertEquals(remoteResults.firstOrNull()?.body, result.firstOrNull()?.body)
    }
}