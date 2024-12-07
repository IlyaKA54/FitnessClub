package com.example.fitnessclub.data.Model

import com.example.fitnessclub.data.Model.Data.Booking
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BookingRepository(private val db: Firebase) {

    fun isUserAlreadyBooked(
        userId: String,
        activityId: String,
        onResult: (Boolean) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.firestore.collection("bookings")
            .whereEqualTo("userId", userId)
            .whereEqualTo("activityId", activityId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                onResult(querySnapshot.isEmpty)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun addBooking(
        booking: Booking,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.firestore.collection("bookings")
            .document(booking.id)
            .set(booking)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun getBookingsByUserId(
        userId: String,
        onSuccess: (List<Booking>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.firestore.collection("bookings")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val bookings = querySnapshot.documents.mapNotNull { it.toObject(Booking::class.java) }
                onSuccess(bookings)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun deleteBooking(
        bookingId: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.firestore.collection("bookings").document(bookingId).delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception -> onFailure(exception) }
    }
}
