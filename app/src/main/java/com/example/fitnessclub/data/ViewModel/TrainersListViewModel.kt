package com.example.fitnessclub.data.ViewModel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.fitnessclub.data.Model.Data.Trainer
import com.example.fitnessclub.data.Model.ImageManager
import com.example.fitnessclub.data.Model.TrainerRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TrainersListViewModel(
    private val _db:Firebase,
    private val onNavigateToBack: () -> Unit
):ViewModel() {
    private val trainerRepository = TrainerRepository(_db)
    private val imageManager = ImageManager()

    private val _trainers = MutableStateFlow<List<Trainer>>(emptyList())
    val trainers: StateFlow<List<Trainer>> = _trainers

    init {
        loadTrainers()
    }

    fun navigateBack() {
        onNavigateToBack()
    }

    private fun loadTrainers() {
        trainerRepository.getAllTrainers(
            onSuccess = { trainers ->
                _trainers.value = trainers
            },
            onFailure = { exception ->
                Log.e("TrainersListViewModel", "Failed to load trainers: ${exception.message}")
            }
        )
    }

    fun getTrainerImage(imageId: String, onImageLoaded: (Bitmap?) -> Unit) {
        _db.firestore.collection("imgs").document(imageId).get()
            .addOnSuccessListener { document ->
                val base64String = document.getString("imageBase64")
                val bitmap = base64String?.let { imageManager.base64ToBitmap(it) }
                onImageLoaded(bitmap)
            }
            .addOnFailureListener { exception ->
                Log.e("TrainersListViewModel", "Failed to load image: ${exception.message}")
                onImageLoaded(null)
            }
    }

}