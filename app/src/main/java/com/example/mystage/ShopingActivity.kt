package com.example.mystage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mystage.databinding.ActivityShopingBinding

class ShopingActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityShopingBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shoping)
        setContentView(binding.root)

        val navController =findNavController(R.id.shoping_actiivity)
        binding.bottomNav.setupWithNavController(navController)
    }
}