package com.george.posts.domain

import com.george.posts.domain.model.PostDM
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class LoadPostsUseCaseTest {

    //Step 1 : Mock the dependencies
    private val repository: PostRepository = mockk()

    //Step 2: Mock the repo results
    private val repositoryResults = listOf(PostDM(id = 1, body = "test"))

    //Step 3: Late init the class in test
    private lateinit var usecase: LoadPostsUseCase

    @Before
    fun `begin each test case with`() {
        usecase = LoadPostsUseCase(repository)
    }

    @Test
    fun `emit data when repository returns success`()= runTest {
        //Given
        coEvery {
            repository.loadPosts()
        } returns flow { emit(repositoryResults) }

        //When
        val result = usecase.execute().last()

        //Then
        assertEquals(repositoryResults.size, result.success?.size)
        assertEquals(repositoryResults.firstOrNull()?.id, result.success?.firstOrNull()?.id)
    }

    @Test
    fun `emit error when repository return error`() = runTest {
        //Given
        val runTimeError = RuntimeException("That hurts!")
        coEvery { repository.loadPosts() } returns flow { throw runTimeError }

        //When
        val result = usecase.execute().last()

        //Then
        assertEquals(runTimeError.localizedMessage.orEmpty(), result.errorMessage.orEmpty())
    }
}