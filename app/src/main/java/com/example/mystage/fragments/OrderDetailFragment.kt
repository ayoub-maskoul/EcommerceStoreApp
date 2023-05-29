package com.example.mystage.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mystage.adapters.BillingProductsAdapter
import com.example.mystage.databinding.FragmentOrderDetailBinding
import com.example.mystage.model.OrderStatus
import com.example.mystage.model.getOrderStatus
import com.example.mystage.util.VerticalItemDecoration
import com.example.mystage.util.hideBottomNavigationView

class OrderDetailFragment : Fragment() {
    private lateinit var binding: FragmentOrderDetailBinding
    private val billingProductsAdapter by lazy { BillingProductsAdapter() }
    private val args by navArgs<OrderDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val order = args.order
        hideBottomNavigationView()

        setupOrderRv()
        binding.imageCloseOrder.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.apply {

            tvOrderId.text = "Order #${order.orderId}"


            stepView.setSteps(
                mutableListOf(
                    OrderStatus.Ordered.status,
                    OrderStatus.Confirmed.status,
                    OrderStatus.Shipped.status,
                    OrderStatus.Delivered.status,
                    OrderStatus.Returned.status,
                )
            )

            val currentOrderState = when (getOrderStatus(order.orderStatus)) {
                is OrderStatus.Ordered -> 0
                is OrderStatus.Confirmed -> 1
                is OrderStatus.Shipped -> 2
                is OrderStatus.Delivered -> 3
                is OrderStatus.Returned -> 4
                else -> 0
            }

            stepView.go(currentOrderState, true)
            if (currentOrderState == 3) {
                stepView.done(true)
            }

            tvFullName.text = order.address.fullName
            tvAddress.text = "${order.address.street} ${order.address.city}"
            tvPhoneNumber.text = order.address.phone

            tvTotalPrice.text = "$ ${order.totalPrice}"

        }

        billingProductsAdapter.differ.submitList(order.products)
    }

    private fun setupOrderRv() {
        binding.rvProducts.apply {
            adapter = billingProductsAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            addItemDecoration(VerticalItemDecoration())
        }
    }
}