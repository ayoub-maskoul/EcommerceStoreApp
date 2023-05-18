package com.example.mystage.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mystage.R
import com.example.mystage.databinding.ActivityShopingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShopingActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityShopingBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navController =findNavController(R.id.shoping_actiivity)
        binding.bottomNav.setupWithNavController(navController)
    }
}