package com.example.fitnessclub.data.Model

import android.util.Log
import com.example.fitnessclub.data.Model.Data.Product
import com.example.fitnessclub.data.Model.Data.Purchase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductRepository(private val db: Firebase) {

    // Получить список продуктов
    fun fetchProducts(
        onSuccess: (List<Product>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.firestore.collection("products").get()
            .addOnSuccessListener { result ->
                val products = result.documents.mapNotNull { it.toObject(Product::class.java) }
                onSuccess(products)
            }
            .addOnFailureListener { exception ->
                onFailure("Failed to fetch products: ${exception.message}")
            }
    }

    fun fetchImageBase64(
        imageID: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.firestore.collection("imgs").document(imageID).get()
            .addOnSuccessListener { document ->
                val base64 = document.getString("imageBase64")
                if (base64 != null) {
                    onSuccess(base64)
                } else {
                    onFailure("Image not found for ID: $imageID")
                }
            }
            .addOnFailureListener { exception ->
                onFailure("Failed to fetch image: ${exception.message}")
            }
    }

    fun getProductsForPurchases(
        purchases: List<Purchase>,
        onSuccess: (List<Pair<Purchase, Product>>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val productsCollection = db.firestore.collection("products")
        val result = mutableListOf<Pair<Purchase, Product>>()
        var processedCount = 0

        purchases.forEach { purchase ->
            productsCollection.document(purchase.productId).get()
                .addOnSuccessListener { document ->
                    val product = document.toObject(Product::class.java)
                    if (product != null) {
                        result.add(purchase to product)
                    }
                    processedCount++
                    if (processedCount == purchases.size) {
                        onSuccess(result)
                    }
                }
                .addOnFailureListener { exception ->
                    processedCount++
                    Log.e("getProductsForPurchases", "Failed to load product: ${exception.message}")
                    if (processedCount == purchases.size) {
                        onSuccess(result) // Возвращаем частичный результат
                    }
                }
        }
    }

}
