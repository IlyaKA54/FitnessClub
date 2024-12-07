package com.example.fitnessclub.data.Model.Data

data class Activity(
    val id: String = "",
    val name: String = "",
    val price: Int = 0,
    val startAt: Long = 0,
    val time: Long = 0,
    val count: Int = 0,
    val description: String? = null,
    val coachId: String? = null
)