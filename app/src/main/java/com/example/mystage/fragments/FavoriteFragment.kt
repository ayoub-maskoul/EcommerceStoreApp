package com.example.mystage.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mystage.R
import com.example.mystage.adapters.FavoriteProductAdapter
import com.example.mystage.databinding.FragmentFavoriteBinding
import com.example.mystage.util.Resource
import com.example.mystage.util.VerticalItemDecoration
import com.example.mystage.viewmodel.FavoriteViewModel
import kotlinx.coroutines.flow.collectLatest

class FavoriteFragment:Fragment(R.layout.fragment_favorite) {
    private lateinit var binding: FragmentFavoriteBinding
    private val favoriteAdapter by lazy { FavoriteProductAdapter() }
    private val viewModel by activityViewModels<FavoriteViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFavoRv()

        lifecycleScope.launchWhenStarted {
            viewModel.favoriteProducts.collectLatest{
                when (it){
                    is Resource.Loading ->{
                        binding.progressbarCart.visibility = View.VISIBLE
                    }
                    is Resource.Success ->{
                        binding.progressbarCart.visibility = View.INVISIBLE
                        favoriteAdapter.differ.submitList(it.data)

                    }
                    is Resource.Error ->{
                        binding.progressbarCart.visibility = View.INVISIBLE
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setupFavoRv() {
        binding.rvCart.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = favoriteAdapter
            addItemDecoration(VerticalItemDecoration())
        }
    }


}