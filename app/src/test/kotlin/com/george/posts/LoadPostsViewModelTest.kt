package com.george.posts

import app.cash.turbine.test
import com.george.core.BusinessStates
import com.george.core.UIStates
import com.george.posts.domain.LoadPostsUseCase
import com.george.posts.domain.PostRepository
import com.george.posts.domain.model.PostDM
import com.george.posts.model.PostUIM
import com.george.posts.viewModel.LoadPostsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoadPostsViewModelTest {

    //Step 1: Mock the dependencies
    private val repository : PostRepository = mockk()
    private val useCase: LoadPostsUseCase = spyk(LoadPostsUseCase(repository))

    //Step 2: Mock the dependencies result
    private val postsDM = listOf(PostDM(id = 1, body = "test"))
    private val postsUIM = listOf(PostUIM(id = 1, body = "test"))


    //Step 3: Late init class in test
    private lateinit var viewModel : LoadPostsViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun `begin each test case with`() {
        viewModel = LoadPostsViewModel(useCase)
    }

    @Test
    fun `emit loading from usecase`() = runTest {
        //Given
        coEvery {
            useCase.execute()
        } returns flow { emit(BusinessStates.Loading()) }

        //When
        viewModel.loadPosts()

        //Then
        viewModel.uiState.test {
            assertEquals(UIStates(isLoading = true), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emit data from usecase`() = runTest {
        //Given
        val success = BusinessStates.Success(postsDM)
        coEvery {
            useCase.execute()
        } returns flow {
            emit(success)
        }

        //When
        viewModel.loadPosts()

        //Then
        viewModel.uiState.test {
            assertEquals(UIStates(success = postsUIM), awaitItem())
        }
    }

    @Test
    fun `emit error from usecase`() = runTest {
        //Given
        val error = BusinessStates.Error<List<PostDM>>("That hurts !")
        coEvery {
            useCase.execute()
        } returns flow {
            emit(error)
        }

        //When
        viewModel.loadPosts()

        //Then
        viewModel.uiState.test {
            assertEquals(UIStates(error = "That hurts !"), awaitItem())
        }
    }
}