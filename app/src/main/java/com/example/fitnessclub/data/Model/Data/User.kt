package com.example.fitnessclub.data.Model.Data

data class User(
    val lastName: String = "",
    val firstName: String = "",
    val middleName: String = "",
    val phoneNumber: String = "",
    var profileImageUrl: String? = null,
    var membershipId: String? = null
)
