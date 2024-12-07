package com.example.fitnessclub.data.Model

import com.example.fitnessclub.data.Model.Data.Membership
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MembershipRepository(private val db: Firebase) {

    fun fetchImageBase64(
        imageId: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.firestore.collection("imgs")
            .document(imageId)
            .get()
            .addOnSuccessListener { document ->
                val base64 = document.getString("imageBase64")
                if (base64 != null) {
                    onSuccess(base64)
                } else {
                    onFailure("Image data not found")
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception.message ?: "Failed to fetch image")
            }
    }

    fun fetchMemberships(
        onSuccess: (List<Membership>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.firestore.collection("memberships")
            .get()
            .addOnSuccessListener { documents ->
                val memberships = documents.mapNotNull { it.toObject(Membership::class.java) }
                onSuccess(memberships)
            }
            .addOnFailureListener { exception ->
                onFailure(exception.message ?: "Failed to fetch memberships")
            }
    }

    fun getMembershipById(id: String, onSuccess: (Membership?) -> Unit, onFailure: (String) -> Unit) {
        db.firestore.collection("memberships").document(id).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val membership = document.toObject(Membership::class.java)
                    onSuccess(membership)
                } else {
                    onFailure("Membership not found")
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception.localizedMessage ?: "Error fetching membership")
            }
    }
}
