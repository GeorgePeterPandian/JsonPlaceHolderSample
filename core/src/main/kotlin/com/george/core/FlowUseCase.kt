package com.george.core

import kotlinx.coroutines.flow.Flow

abstract class FlowUseCase<T> {
    abstract suspend fun execute(): Flow<T>
}