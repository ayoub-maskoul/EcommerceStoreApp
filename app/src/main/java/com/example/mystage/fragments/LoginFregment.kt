package com.example.mystage.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mystage.R
import com.example.mystage.view.ShopingActivity
import com.example.mystage.databinding.FragmentLoginBinding
import com.example.mystage.util.Resource
import com.example.mystage.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFregment:Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModule by viewModels<LoginViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginPageSign.setOnClickListener {
            findNavController().navigate(R.id.action_loginFregment_to_singupFregment)
        }
        binding.apply {
            login.setOnClickListener {
                val email = emailLogin.text.toString().trim()
                val password = passwordLogin.text.toString()
                viewModule.login(email, password)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModule.login.collect{
                when(it){
                    is Resource.Loading -> {
                        binding.login.startAnimation()

                    }
                    is Resource.Success -> {
                        binding.login.revertAnimation()
                        Intent(requireActivity(), ShopingActivity::class.java).also {
                            intent->intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }
                    is Resource.Error -> {
                        binding.login.revertAnimation()
                        Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                    }
                    else -> Unit
                }
            }
        }
    }
}