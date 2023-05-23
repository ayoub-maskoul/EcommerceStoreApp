package com.example.mystage.fragments.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mystage.R
import com.example.mystage.adapters.BestProductsAdapter
import com.example.mystage.databinding.FragmentBaseCategoryBinding
import com.example.mystage.util.showBottomNavigationView
import com.example.mystage.viewmodel.MainCategoryViewModel



open class BaseCategoryFragment:Fragment(R.layout.fragment_base_category) {
    private lateinit var binding: FragmentBaseCategoryBinding
    protected val  bestProductsAdapter: BestProductsAdapter by lazy { BestProductsAdapter() }
    private val viewModel by viewModels<MainCategoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBestProduct()

        bestProductsAdapter.onClick = {
            val b = Bundle().apply { putParcelable("product",it) }
            findNavController().navigate(R.id.action_homeFragment_to_productDetailsFragment,b)
        }


    }



    private fun hideLoading() {
        binding.bestProductsProgressbar.visibility= View.GONE
    }

    private fun showLoading() {
        binding.bestProductsProgressbar.visibility= View.VISIBLE
    }

    fun showBestProductsLoading(){
        binding.bestProductsProgressbar.visibility = View.VISIBLE
    }

    fun hideBestProductsLoading(){
        binding.bestProductsProgressbar.visibility = View.GONE
    }
    open fun onOfferPagingRequest(){

    }

    open fun onBestProductsPagingRequest(){

    }

    private fun setupBestProduct() {

        binding.baesCategory.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 1, GridLayoutManager.VERTICAL, false)
            adapter = bestProductsAdapter
        }
    }
    override fun onResume() {
        super.onResume()
        showBottomNavigationView()
    }
}