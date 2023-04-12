package com.george.posts.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.george.core.BusinessStates
import com.george.core.UIStates
import com.george.posts.domain.LoadPostsUseCase
import com.george.posts.domain.model.PostDM
import com.george.posts.model.PostUIM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoadPostsViewModel @Inject constructor(private val useCase: LoadPostsUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(UIStates())
    val uiState = _uiState.asStateFlow()

    suspend fun loadPosts() {
        useCase.execute().onEach { result ->
            when (result) {
                is BusinessStates.Loading -> _uiState.value = UIStates(isLoading = true)
                is BusinessStates.Success -> _uiState.value =
                    UIStates(success = result.success!!.toUIMs())
                is BusinessStates.Error -> _uiState.value =
                    UIStates(error = result.errorMessage.orEmpty())
            }
        }.launchIn(viewModelScope)
    }
}

internal fun List<PostDM>.toUIMs() = map { it.toPostUIM() }

private fun PostDM.toPostUIM() = PostUIM(id = id, body = body)