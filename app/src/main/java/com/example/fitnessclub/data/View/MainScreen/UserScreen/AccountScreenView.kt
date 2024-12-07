package com.example.fitnessclub.data.View.MainScreen.UserScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.fitnessclub.R
import com.example.fitnessclub.data.ViewModel.AccountScreenViewModel
import com.example.fitnessclub.data.widgets.ActionButton

@Composable
fun AccountScreenView(
    viewModel: AccountScreenViewModel,
    onNavigate: (String) -> Unit
) {
    val username by viewModel.username.observeAsState("Пользователь")
    val profileImage by viewModel.profileImageBitmap

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .padding(16.dp)
        ) {
            Text(
                text = "Account",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )

            IconButton(
                onClick = { Log.d("AccountScreenView", "Settings clicked") },
                modifier = Modifier.align(Alignment.CenterEnd).padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.Gray)
                .clickable {
                    onNavigate("edit_account_screen")
                },
            contentAlignment = Alignment.Center
        ) {
            if (profileImage != null) {
                Image(
                    bitmap = profileImage!!.asImageBitmap(),
                    contentDescription = "Profile Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = "Default Profile Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Имя пользователя
        Text(
            text = username,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Кнопки действий
        ActionButton(
            text = "Edit",
            icon = Icons.Default.Star,
            onClick = { onNavigate("edit_account_screen") }
        )
        Spacer(modifier = Modifier.height(5.dp))
        ActionButton(
            text = "Membership",
            icon = Icons.AutoMirrored.Filled.List,
            onClick = { onNavigate("membership_controller") }
        )
        Spacer(modifier = Modifier.height(5.dp))
        ActionButton(
            text = "Purchase History",
            icon = Icons.Default.Info,
            onClick = { onNavigate("purchases_items")}
        )

        Spacer(modifier = Modifier.height(5.dp))
        ActionButton(
            text = "Bookings",
            icon = Icons.Default.DateRange,
            onClick = { onNavigate("booking_items")}
        )

        Spacer(modifier = Modifier.height(5.dp))
        ActionButton(
            text = "Trainers",
            icon = Icons.Default.Face,
            onClick = { onNavigate("trainers_list")}
        )
    }
}
