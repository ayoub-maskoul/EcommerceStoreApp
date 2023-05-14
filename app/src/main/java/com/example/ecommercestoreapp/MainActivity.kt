package com.example.ecommercestoreapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var btn_sinup:Button
    lateinit var btn_login:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_sinup = findViewById(R.id.sing_page)
        btn_login = findViewById(R.id.login_page)
        btn_sinup.setOnClickListener {
            val intent:Intent = Intent(this,SingupActivity::class.java)
            startActivity(intent)
        }
        btn_login.setOnClickListener {
            val intent:Intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }
}