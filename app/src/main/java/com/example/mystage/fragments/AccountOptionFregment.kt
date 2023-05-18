package com.example.mystage.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mystage.R
import com.example.mystage.view.ShopingActivity
import com.example.mystage.databinding.FragmentAccountOptionsBinding
import com.example.mystage.viewmodel.AccountOptionViewModel
import com.example.mystage.viewmodel.AccountOptionViewModel.Companion.SHOPPING_ACTIVITY
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AccountOptionFregment:Fragment(R.layout.fragment_account_options) {

    private lateinit var binding: FragmentAccountOptionsBinding
    private val viewModels by viewModels<AccountOptionViewModel>()
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
        lifecycleScope.launchWhenStarted {
            viewModels.navigate.collect {
                when (it) {
                    SHOPPING_ACTIVITY -> {
                        Intent(requireActivity(), ShopingActivity::class.java).also { intent ->
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }

                    R.id.action_accountOptionFregment_to_loginFregment -> {
                        findNavController().navigate(it)
                    }

                    else -> Unit
                }
            }
        }
        binding.loginPage.setOnClickListener {
            findNavController().navigate(R.id.action_accountOptionFregment_to_loginFregment)
        }
        binding.singPage.setOnClickListener {
            findNavController().navigate(R.id.action_accountOptionFregment_to_singupFregment)
        }
    }
}