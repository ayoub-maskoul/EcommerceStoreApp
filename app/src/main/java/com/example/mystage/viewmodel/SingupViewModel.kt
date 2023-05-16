package com.example.mystage.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mystage.model.User
import com.example.mystage.util.Constants.USER_COLLECTION
import com.example.mystage.util.Ress
import com.example.mystage.util.SignupFailed
import com.example.mystage.util.SignupValidation
import com.example.mystage.util.validateEmail
import com.example.mystage.util.validatePassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@HiltViewModel
class SingupViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db:FirebaseFirestore
) : ViewModel(){

    private val _regisrt =  MutableStateFlow<Ress<User>>(Ress.Unspecified())
    val regisrt :Flow<Ress<User>> = _regisrt

    private val _valid = Channel<SignupFailed>()
    val valid = _valid.receiveAsFlow()

    fun creatAccount(user: User,password :String){
        if (checkVakidation(user, password)){
        runBlocking {
            _regisrt.emit(Ress.Loading())
        }
        firebaseAuth.createUserWithEmailAndPassword(user.email, password)
            .addOnSuccessListener {
                it.user?.let {
                    saveUser(it.uid,user)
                }
            }
            .addOnFailureListener {
                _regisrt.value = Ress.Error(it.message.toString())
            }
        }else{
            val signupFailed = SignupFailed(
                validateEmail(user.email),validatePassword(password)
            )
            runBlocking {
                _valid.send(signupFailed)

            }
        }
    }

    private fun saveUser(userid: String,user: User) {
        db.collection(USER_COLLECTION)
            .document(userid)
            .set(user)
            .addOnSuccessListener {
                _regisrt.value = Ress.Success(user)
            }.addOnFailureListener {
                _regisrt.value = Ress.Error(it.message.toString())
            }

    }

    private fun checkVakidation(user: User, password: String):Boolean {
        val emailValidation = validateEmail(user.email)
        val passwordValidation = validatePassword(password)
        val sholdSignup = emailValidation is SignupValidation.Success &&
                passwordValidation is SignupValidation.Success
        return sholdSignup
    }
}