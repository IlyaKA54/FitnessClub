package com.example.fitnessclub.data.Model

import com.example.fitnessclub.data.Model.Data.User
import com.example.fitnessclub.data.View.MainScreen.MainScreenDataObject
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserRepository(db: Firebase) {
    private val _usersPath =  "users"

    private val _firebaseAuth = db.auth
    private val _firestore = db.firestore

    fun sendPasswordResetEmail(
        email: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        _firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess("A password reset email has been sent. Please check your email and follow the instructions.")
                } else {
                    val errorMessage = task.exception?.message ?: "Reset failed"
                    onFailure(errorMessage)
                }
            }
            .addOnFailureListener {
                onFailure(it.message ?: "Reset failed")
            }
    }

    fun getUserById(uid: String, onSuccess: (User?) -> Unit, onFailure: (Exception) -> Unit) {
        _firestore.collection(_usersPath).document(uid).get()
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    val user = task.result.toObject(User::class.java)
                    onSuccess(user)
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun createUser(uid: String, user: User, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        _firestore.collection(_usersPath).document(uid).set(user)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun checkIfUserExists(uid: String, onSuccess: (Boolean) -> Unit, onFailure: (Exception) -> Unit) {
        _firestore.collection(_usersPath).document(uid).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess(task.result.exists())
                } else {
                    onFailure(task.exception ?: Exception("Unknown error"))
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun updateUser(uid: String, updatedUser: User, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        _firestore.collection(_usersPath).document(uid).set(updatedUser)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception -> onFailure(exception) }
    }

    fun updateUserMembership(
        membershipId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val userRef = _firestore.collection("users").document(_firebaseAuth.currentUser?.uid ?: "")
        userRef.update("membershipId", membershipId)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception ->
                onFailure("Failed to update membership: ${exception.message}")
            }
    }
}