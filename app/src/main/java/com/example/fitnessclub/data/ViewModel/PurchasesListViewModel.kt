package com.example.fitnessclub.data.ViewModel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnessclub.data.Model.Data.Product
import com.example.fitnessclub.data.Model.Data.Purchase
import com.example.fitnessclub.data.Model.ImageManager
import com.example.fitnessclub.data.Model.ProductRepository
import com.example.fitnessclub.data.Model.PurchaseRepository
import com.example.fitnessclub.data.View.MainScreen.MainScreenDataObject
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PurchasesListViewModel(
    private val _db: Firebase,
    private val _navData: MainScreenDataObject,
    private val onNavigateToBack: () -> Unit
) : ViewModel() {

    private val purchaseRepository = PurchaseRepository(_db)
    private val productRepository = ProductRepository(_db)
    private val imageManager = ImageManager()

    private val _purchases = MutableStateFlow<List<Pair<Purchase, Product>>>(emptyList())
    val purchases: StateFlow<List<Pair<Purchase, Product>>> = _purchases

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        loadUserPurchases()
    }

    private fun loadUserPurchases() {
        purchaseRepository.getPurchasesByUserId(
            _navData.uid,
            onSuccess = { purchases ->
                loadProductsForPurchases(purchases)
            },
            onFailure = { exception ->
                _error.value = "Failed to load purchases: ${exception.message}"
            }
        )
    }

    private fun loadProductsForPurchases(purchases: List<Purchase>) {
        productRepository.getProductsForPurchases(
            purchases,
            onSuccess = { purchaseProductPairs ->
                _purchases.value = purchaseProductPairs
            },
            onFailure = { exception ->
                _error.value = "Failed to load products: ${exception.message}"
            }
        )
    }

    fun loadImage(imageId: String, onImageLoaded: (Bitmap?) -> Unit) {
        _db.firestore.collection("imgs").document(imageId).get()
            .addOnSuccessListener { document ->
                val base64String = document.getString("imageBase64")
                if (!base64String.isNullOrEmpty()) {
                    onImageLoaded(imageManager.base64ToBitmap(base64String))
                } else {
                    onImageLoaded(null)
                }
            }
            .addOnFailureListener {
                onImageLoaded(null)
            }
    }

    fun navigateBack(){
        onNavigateToBack()
    }
}
