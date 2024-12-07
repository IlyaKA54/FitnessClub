package com.example.fitnessclub.data.Model

import com.example.fitnessclub.data.Model.Data.Trainer
import com.example.fitnessclub.data.Model.Data.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class TrainerRepository(private val db: Firebase) {

    fun getTrainerById(uid: String, onSuccess: (Trainer?) -> Unit, onFailure: (Exception) -> Unit) {
        db.firestore.collection("trainers").document(uid).get()
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    val coach = task.result.toObject(Trainer::class.java)
                    onSuccess(coach)
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun getAllTrainers(
        onSuccess: (List<Trainer>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.firestore.collection("trainers").get()
            .addOnSuccessListener { querySnapshot ->
                val trainers = querySnapshot.documents.mapNotNull { it.toObject(Trainer::class.java) }
                onSuccess(trainers)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}