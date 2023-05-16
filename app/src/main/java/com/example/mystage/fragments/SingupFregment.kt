package com.example.mystage.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mystage.R
import com.example.mystage.databinding.FragmentSingupBinding
import com.example.mystage.model.User
import com.example.mystage.util.Ress
import com.example.mystage.util.SignupValidation
import com.example.mystage.viewmodel.SingupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class SingupFregment:Fragment() {

    private lateinit var binding: FragmentSingupBinding
    private val viewModule by viewModels<SingupViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingupBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.entrylLog.setOnClickListener {
            findNavController().navigate(R.id.action_singupFregment_to_loginFregment)
        }
        binding.apply {
            singUp.setOnClickListener {
                val user = User(
                    fnNameSing.text.toString().trim(),
                    lNameSing.text.toString().trim(),
                    emailSing.text.toString().trim(),
                )
                val password = passwordSing.text.toString()
                viewModule.creatAccount(user,password)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModule.regisrt.collect{
                when(it){
                    is Ress.Loading ->{
                        binding.singUp.startAnimation()
                    }
                    is Ress.Success ->{
                        Log.d("test",it.data.toString())
                        binding.singUp.revertAnimation()
                    }
                    is Ress.Error ->{
                        Log.d("test",it.message.toString())
                    }
                    else ->Unit

                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModule.valid.collect{ valid ->
                if (valid.email is SignupValidation.Failed){
                    withContext(Dispatchers.Main){
                        binding.emailSing.apply {
                            requestFocus()
                            error = valid.email.message
                        }
                    }
                }
                if (valid.password is SignupValidation.Failed){
                    withContext(Dispatchers.Main){
                        binding.passwordSing.apply {
                            requestFocus()
                            error = valid.password.message
                        }
                    }
                }
            }
        }
    }
}