package com.example.mystage.util

import androidx.fragment.app.Fragment
import com.example.mystage.R
import com.example.mystage.view.ShopingActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

fun Fragment.hideBottomNavigationView(){
    val bottomNavigationView =
        (activity as ShopingActivity).findViewById<BottomNavigationView>(
            R.id.bottomNav
        )
    bottomNavigationView.visibility = android.view.View.GONE
}

fun Fragment.showBottomNavigationView(){
    val bottomNavigationView = (activity as ShopingActivity).findViewById<BottomNavigationView>(
            R.id.bottomNav
        )
    bottomNavigationView.visibility = android.view.View.VISIBLE
}