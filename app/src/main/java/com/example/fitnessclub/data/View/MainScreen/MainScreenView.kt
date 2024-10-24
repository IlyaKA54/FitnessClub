package com.example.fitnessclub.data.View.MainScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fitnessclub.data.widgets.BottomMenu

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreenView(navData: MainScreenDataObject) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomMenu()
        }
    ) {

    }
}