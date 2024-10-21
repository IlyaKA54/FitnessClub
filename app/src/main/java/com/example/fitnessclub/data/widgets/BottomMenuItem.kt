package com.example.fitnessclub.data.widgets

import com.example.fitnessclub.R

sealed class BottomMenuItem(
    val route: String,
    val title: String,
    val iconId: Int
) {
    object  Purchase : BottomMenuItem(
        route = "purhase",
        title = "Purchase",
        iconId = R.drawable.ic_shopping_bag
    )

    object  Schedule : BottomMenuItem(
        route = "schedule",
        title = "Schedule",
        iconId = R.drawable.ic_calendar
    )

    object  Account : BottomMenuItem(
        route = "account",
        title = "Account",
        iconId = R.drawable.ic_person
    )
}