package com.example.fitnessclub.data.Model

import com.example.fitnessclub.data.Model.Data.Purchase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore

class PurchaseRepository(private val db: com.google.firebase.ktx.Firebase) {
    fun addPurchase(
        purchase: Purchase,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.firestore.collection("purchases").document(purchase.id).set(purchase)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception ->
                onFailure("Failed to add purchase: ${exception.message}")
            }
    }

    fun getPurchasesByUserId(
        userId: String,
        onSuccess: (List<Purchase>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.firestore.collection("purchases")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val purchases = querySnapshot.documents.mapNotNull { document ->
                    document.toObject(Purchase::class.java)
                }
                onSuccess(purchases)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}