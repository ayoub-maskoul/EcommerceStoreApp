package com.example.mystage.util

import android.util.Patterns

fun validateEmail(email:String):SignupValidation{
    if (email.isEmpty()){
        return SignupValidation.Failed("Entry Email")
    }
    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        return SignupValidation.Failed("Wrong Email")
    return SignupValidation.Success
}
fun validatePassword(password:String):SignupValidation{
    if (password.isEmpty()){
        return SignupValidation.Failed("Entry Password")
    }
    if(password.length<5)
        return SignupValidation.Failed("Password Short")
    return SignupValidation.Success
}