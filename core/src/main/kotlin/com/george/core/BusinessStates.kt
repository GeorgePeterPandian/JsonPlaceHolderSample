package com.george.core

sealed class BusinessStates<T>(val success :T ?= null, val errorMessage:String? =null){
    class Loading<T>:BusinessStates<T>()
    class Success<T>(data: T):BusinessStates<T>(success = data)
    class Error<T>(errorMessage: String):BusinessStates<T>(errorMessage = errorMessage)
}
