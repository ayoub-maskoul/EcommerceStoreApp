package com.example.mystage.util

sealed class Ress<T>(
    val data: T? = null,
    val message:String? = null
){
    class Success<T>(data: T): Ress<T>(data)
    class Error<T>(message: String): Ress<T>(message = message)
    class Loading<T>: Ress<T>()
    class Unspecified<T>: Ress<T>()

}
