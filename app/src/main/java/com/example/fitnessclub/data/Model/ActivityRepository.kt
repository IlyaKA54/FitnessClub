package com.example.fitnessclub.data.Model

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fitnessclub.data.Model.Data.Activity
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.ZoneId

class ActivityRepository(private val db: Firebase) {

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getActivitiesByDate(selectedDate: LocalDate): List<Activity> {
        // Переводим дату в Unix метку (начало дня)
        val startOfDay = selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        // Переводим дату в Unix метку (конец дня)
        val endOfDay = selectedDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

        // Запрос к коллекции "activities", где тренировки должны попадать в выбранный день
        val snapshot = db.firestore .collection("activities")
            .whereGreaterThanOrEqualTo("startAt", startOfDay)
            .whereLessThanOrEqualTo("startAt", endOfDay)
            .get()
            .await()

        return snapshot.documents.mapNotNull { document ->
            document.toObject(Activity::class.java)?.copy(id = document.id)
        }
    }

    fun getActivityById(activityId: String, onSuccess: (Activity?) -> Unit, onFailure: (Exception) -> Unit) {
        db.firestore.collection("activities").document(activityId).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val activity = task.result.toObject(Activity::class.java)
                    onSuccess(activity)
                } else {
                    onFailure(Exception("Failed to fetch activity"))
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }


}