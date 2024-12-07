package com.example.fitnessclub.data.Model

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class StorageRepository(private val firebase: Firebase) {

    fun uploadProfileImage(uid: String, imageUri: Uri, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        val storageRef = firebase.storage.reference.child("user_images/$uid.jpg")

        storageRef.putFile(imageUri)
            .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    onSuccess(uri.toString())
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }


}