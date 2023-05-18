package com.example.mystage.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mystage.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}