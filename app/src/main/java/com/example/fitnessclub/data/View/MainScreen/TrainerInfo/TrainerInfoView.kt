package com.example.fitnessclub.data.View.MainScreen.TrainerInfo

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.fitnessclub.data.ViewModel.TrainerInfoViewModel
import com.example.fitnessclub.data.widgets.BookingDialog
import com.example.fitnessclub.ui.theme.ButtonColorPart1

@Composable
fun TrainerInfoView(trainerInfoViewModel: TrainerInfoViewModel) {
    val trainer = trainerInfoViewModel.trainer
    val profileImage by trainerInfoViewModel.profileImage.collectAsState()
    var showBookingDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth().height(1 / 4f * LocalConfiguration.current.screenHeightDp.dp)) {
            profileImage?.let {
                Image(
                    painter = BitmapPainter(it.asImageBitmap()),
                    contentDescription = "Trainer Image",
                    modifier = Modifier.fillMaxSize()
                )
            } ?: Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray)
            )

            IconButton(
                onClick = { trainerInfoViewModel.navigateBack() },
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }

        trainer?.let {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "${it.lastName} ${it.firstName} ${it.middleName}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Phone: ${it.phoneNumber}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    )
    {

        Button(
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonColorPart1
            ), onClick = { showBookingDialog = true },
        ) {
            Text(text = "Sign up for training")
        }
    }

    if (showBookingDialog) {
        BookingDialog(
            onDismiss = { showBookingDialog = false },
            onBook = { dateTime ->
                trainerInfoViewModel.bookTraining(
                    dateTime,
                    onSuccess = { showBookingDialog = false },
                    onFailure = { exception ->
                        Log.e("TrainerInfoView", "Failed to book training: ${exception.message}")
                    }
                )
            }
        )

    }
}
