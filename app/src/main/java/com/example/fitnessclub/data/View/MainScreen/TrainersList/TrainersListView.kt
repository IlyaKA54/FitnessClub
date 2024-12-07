package com.example.fitnessclub.data.View.MainScreen.TrainersList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fitnessclub.data.Model.Data.Trainer
import com.example.fitnessclub.data.ViewModel.TrainersListViewModel
import com.example.fitnessclub.data.widgets.TrainerItemList

@Composable
fun TrainersListView(
    trainersListViewModel: TrainersListViewModel,
    onNavigate:(String, Trainer) -> Unit){

    val trainers by trainersListViewModel.trainers.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .padding(16.dp)
        ) {
            Text(
                text = "Trainers",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.Center)
            )
            IconButton(
                onClick = { trainersListViewModel.navigateBack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(trainers) { trainerObj ->
                TrainerItemList(
                    trainer = trainerObj,
                    loadImage = trainersListViewModel::getTrainerImage,
                    onClic = {trainer ->
                        onNavigate("trainer_info", trainer)
                    }
                )
            }
        }
    }

}