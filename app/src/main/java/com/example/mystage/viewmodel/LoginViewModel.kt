package com.example.mystage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystage.util.Resource
import com.example.mystage.util.SignupFailed
import com.example.mystage.util.SignupValidation
import com.example.mystage.util.validateEmail
import com.example.mystage.util.validatePassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) :ViewModel() {
    private val _login = MutableSharedFlow<Resource<FirebaseUser>>()
    val login = _login.asSharedFlow()


    private val _valid = Channel<SignupFailed>()
    val valid = _valid.receiveAsFlow()

    fun login (email:String,password:String){
        if (checkVakidation(email, password)){
            viewModelScope.launch { _login.emit(Resource.Loading()) }
            firebaseAuth.signInWithEmailAndPassword(
                email, password
            ).addOnSuccessListener {
                viewModelScope.launch {
                    it.user?.let {
                        _login.emit(Resource.Success(it))
                    }
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _login.emit(Resource.Error(it.message.toString()))

                }
            }
        }else{
            val signupFailed = SignupFailed(
                validateEmail(email),validatePassword(password)
            )
            runBlocking {
                _valid.send(signupFailed)

            }
        }

    }
    private fun checkVakidation(email: String, password: String):Boolean {
        val emailValidation = validateEmail(email)
        val passwordValidation = validatePassword(password)
        val sholdSignup = emailValidation is SignupValidation.Success &&
                passwordValidation is SignupValidation.Success
        return sholdSignup
    }
}