package com.example.mystage.util

sealed class SignupValidation(){
    object Success: SignupValidation()
    data class Failed(val message:String):SignupValidation()
}

data class SignupFailed(
    val email :SignupValidation,
    val password:SignupValidation
)


