package com.example.mystage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystage.firebase.FirebaseCommon
import com.example.mystage.helper.getProductPrice
import com.example.mystage.model.CartProduct
import com.example.mystage.model.Product
import com.example.mystage.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel  @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val firebaseCommon: FirebaseCommon
) : ViewModel() {

    private val _favoriteProducts =
        MutableStateFlow<Resource<List<CartProduct>>>(Resource.Unspecified())
    val favoriteProducts = _favoriteProducts.asStateFlow()


    val productsPrice = favoriteProducts.map {
        when (it) {
            is Resource.Success -> {
                calculatePrice(it.data!!)
            }
            else -> null
        }
    }
    private val _deleteDialog = MutableSharedFlow<CartProduct>()
    val deleteDialog = _deleteDialog.asSharedFlow()

    private var cartProductDocuments = emptyList<DocumentSnapshot>()


    fun deleteCartProduct(product: Product) {
            firestore.collection("user").document(auth.uid!!).collection("favorite")
                .document(product.id).delete()

    }


    private fun calculatePrice(data: List<CartProduct>): Float {
        return data.sumByDouble { cartProduct ->
            (cartProduct.product.offerPercentage.getProductPrice(cartProduct.product.price) * cartProduct.quantity).toDouble()
        }.toFloat()
    }


    init {
        getFavoriteProducts()
    }


    private fun getFavoriteProducts() {
        viewModelScope.launch { _favoriteProducts.emit(Resource.Loading()) }
        firestore.collection("user").document(auth.uid!!).collection("favorite")
            .addSnapshotListener { value, error ->
                if (error != null || value == null) {
                    viewModelScope.launch { _favoriteProducts.emit(Resource.Error(error?.message.toString())) }
                } else {
                    cartProductDocuments = value.documents
                    val cartProducts = value.toObjects(CartProduct::class.java)
                    viewModelScope.launch { _favoriteProducts.emit(Resource.Success(cartProducts)) }
                }
            }
    }





}