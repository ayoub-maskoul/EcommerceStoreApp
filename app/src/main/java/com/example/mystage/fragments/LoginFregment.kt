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
import com.example.mystage.ShopingActivity
import com.example.mystage.databinding.FragmentLoginBinding
import com.example.mystage.databinding.FragmentSingupBinding
import com.example.mystage.util.Ress
import com.example.mystage.viewmodel.LoginViewModel
import com.example.mystage.viewmodel.SingupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

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
                    is Ress.Loading -> {
                        binding.login.startAnimation()

                    }
                    is Ress.Success -> {
                        binding.login.revertAnimation()
                        Intent(requireActivity(),ShopingActivity::class.java).also {
                            intent->intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }
                    is Ress.Error -> {
                        binding.login.revertAnimation()
                        Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                    }
                    else -> Unit
                }
            }
        }
    }
}