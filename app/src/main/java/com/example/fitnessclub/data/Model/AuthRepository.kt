package com.example.fitnessclub.data.Model

import com.example.fitnessclub.data.View.MainScreen.MainScreenDataObject
import com.google.firebase.auth.FirebaseAuth

class AuthRepository(private val auth: FirebaseAuth) {

    fun signIn(
        email: String,
        password: String,
        onSuccess: (MainScreenDataObject) -> Unit,
        onFailure: (String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess(
                        MainScreenDataObject(
                            task.result.user?.uid!!,
                            task.result.user?.email!!
                        )
                    )
                }
            }
            .addOnFailureListener{
                onFailure(it.message ?: "Authentication failed")
            }
    }
}