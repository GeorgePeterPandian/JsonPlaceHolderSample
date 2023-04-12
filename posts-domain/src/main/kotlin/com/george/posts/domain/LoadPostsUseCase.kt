package com.george.posts.domain

import com.george.core.BusinessStates
import com.george.core.FlowUseCase
import com.george.posts.domain.model.PostDM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoadPostsUseCase @Inject constructor(private val repository: PostRepository) :
    FlowUseCase<BusinessStates<List<PostDM>>>() {

    override suspend fun execute(): Flow<BusinessStates<List<PostDM>>> = flow {
        emit(BusinessStates.Loading())
        try {
            repository.loadPosts().collect { emit(BusinessStates.Success(it)) }
        } catch (exception: Exception) {
            emit(BusinessStates.Error(exception.localizedMessage.orEmpty()))
        }
    }.flowOn(Dispatchers.IO)
}