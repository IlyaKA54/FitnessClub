package com.example.fitnessclub.data.Model

import com.example.fitnessclub.data.View.MainScreen.MainScreenDataObject
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthRepository(firebase: Firebase) {

    private val _auth: FirebaseAuth = firebase.auth

    fun signIn(
        email: String,
        password: String,
        onSuccess: (MainScreenDataObject) -> Unit,
        onFailure: (String) -> Unit
    ) {
        _auth.signInWithEmailAndPassword(email, password)
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

    fun signUp(
        email: String,
        password: String,
        onSuccess: (MainScreenDataObject) -> Unit,
        onFailure: (String) -> Unit
    ) {
        _auth.createUserWithEmailAndPassword(email, password)
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

    fun signOut() {
        _auth.signOut()
    }
}