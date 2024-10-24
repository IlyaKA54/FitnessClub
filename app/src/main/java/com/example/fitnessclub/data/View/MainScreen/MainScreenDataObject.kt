package com.example.fitnessclub.data.View.MainScreen

import kotlinx.serialization.Serializable

@Serializable
data class MainScreenDataObject(
    val uid: String = "",
    val email: String = "",
)
