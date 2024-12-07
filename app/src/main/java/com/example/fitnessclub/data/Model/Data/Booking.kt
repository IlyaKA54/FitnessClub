package com.example.fitnessclub.data.Model.Data

data class Booking (
    val id: String = "",
    val coachId: String? = null,
    val activityId: String? = null,
    val userId: String = "",
    val startAt: Long = 0,
    val time: Long =0,
)