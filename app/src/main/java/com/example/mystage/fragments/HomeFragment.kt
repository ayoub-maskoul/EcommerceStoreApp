package com.example.mystage.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mystage.adapters.HomeViewpageAdapter
import com.example.mystage.databinding.FragmentHomeBinding
import com.example.mystage.fragments.categories.AccesoriesFragment
import com.example.mystage.fragments.categories.ClothesFragment
import com.example.mystage.fragments.categories.JeansFragment
import com.example.mystage.fragments.categories.MainCategoryFragment
import com.example.mystage.fragments.categories.PantsFragment
import com.example.mystage.fragments.categories.ShortsFragment
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment: Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoriesFragments = arrayListOf<Fragment>(
            MainCategoryFragment(),
            ClothesFragment(),
            JeansFragment(),
            PantsFragment(),
            ShortsFragment(),
            AccesoriesFragment(),
        )
        val viewPage2Adapter = HomeViewpageAdapter(categoriesFragments,childFragmentManager,lifecycle)

        binding.viewpagerHome.adapter = viewPage2Adapter
        TabLayoutMediator(binding.tabLayout,binding.viewpagerHome){tab,postion->
            when (postion){
                0->tab.text = "Home"
                1->tab.text = "Clothes"
                2->tab.text = "Jeans"
                3->tab.text = "Pants"
                4->tab.text = "Shorts"
                5->tab.text = "Accessories"
            }

        }.attach()
    }
}