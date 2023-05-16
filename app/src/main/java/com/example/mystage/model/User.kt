package com.example.mystage.model

data class User(
    val firstName:String,
    val lastName:String,
    val email:String,
){
    constructor():this("","","")
}
