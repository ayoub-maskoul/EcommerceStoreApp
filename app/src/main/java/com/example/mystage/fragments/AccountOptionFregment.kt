package com.example.mystage.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mystage.R
import com.example.mystage.databinding.FragmentAccountOptionsBinding
import com.example.mystage.databinding.FragmentLoginBinding
import com.example.mystage.viewmodel.LoginViewModel

class AccountOptionFregment:Fragment() {
    private lateinit var binding: FragmentAccountOptionsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountOptionsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginPage.setOnClickListener {
            findNavController().navigate(R.id.action_accountOptionFregment_to_loginFregment)
        }
        binding.singPage.setOnClickListener {
            findNavController().navigate(R.id.action_accountOptionFregment_to_singupFregment)
        }
    }
}