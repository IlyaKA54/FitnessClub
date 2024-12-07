package com.example.fitnessclub.data.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.fitnessclub.data.Model.Data.Product
import com.example.fitnessclub.data.Model.Data.Purchase
import com.example.fitnessclub.data.Model.ProductRepository
import com.example.fitnessclub.data.Model.PurchaseRepository
import com.example.fitnessclub.data.View.MainScreen.MainScreenDataObject
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PurchaseScreenViewModel(
    private val _db: Firebase,
    private val _data: MainScreenDataObject
) : ViewModel() {

    private val _productRepository = ProductRepository(_db)
    private val _purchaseRepository = PurchaseRepository(_db)

    private val _products = mutableStateOf<List<Product>>(emptyList())
    val products: State<List<Product>> = _products

    private val _error = mutableStateOf("")
    val error: State<String> = _error

    init {
        fetchProducts()
    }

    // Загрузка списка продуктов
    private fun fetchProducts() {
        _productRepository.fetchProducts(
            onSuccess = { productList ->
                _products.value = productList
            },
            onFailure = { errorMessage ->
                _error.value = errorMessage
            }
        )
    }

    fun purchaseProduct(productId: String, onSuccess: () -> Unit) {
        val purchase = Purchase(
            id = _db.firestore.collection("purchases").document().id,
            productId = productId,
            userId = _data.uid,
            time = System.currentTimeMillis()
        )

        _purchaseRepository.addPurchase(
            purchase = purchase,
            onSuccess = onSuccess,
            onFailure = { errorMessage ->
                _error.value = errorMessage
            }
        )
    }

    fun fetchImageBase64(
        imageID: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        _productRepository.fetchImageBase64(
            imageID = imageID,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }
}
