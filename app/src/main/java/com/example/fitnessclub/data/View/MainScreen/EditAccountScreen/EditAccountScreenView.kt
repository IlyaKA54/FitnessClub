package com.example.fitnessclub.data.View.MainScreen.EditAccountScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.fitnessclub.R
import com.example.fitnessclub.data.ViewModel.EditAccountScreenViewModel
import com.example.fitnessclub.data.widgets.RoundedCornerTextField
import com.example.fitnessclub.ui.theme.ButtonColorPart1

@Composable
fun EditAccountScreenView(
    editAccountScreenViewModel: EditAccountScreenViewModel
) {
    val lastName by editAccountScreenViewModel.lastName
    val firstName by editAccountScreenViewModel.firstName
    val middleName by editAccountScreenViewModel.middleName
    val phoneNumber by editAccountScreenViewModel.phoneNumber
    val profileImage by editAccountScreenViewModel.profileImageBitmap
    val error by editAccountScreenViewModel.error

    val _cv = LocalContext.current.contentResolver

    val getImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            editAccountScreenViewModel.setSelectedImageUri(it, _cv)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        IconButton(
            onClick = { editAccountScreenViewModel.navigateBack() },
            modifier = Modifier
                .align(Alignment.Start)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Gray,
                modifier = Modifier.size(50.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Edit Account",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.Gray)
                .clickable {
                    getImageLauncher.launch("image/*")
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

        Spacer(modifier = Modifier.height(10.dp))

        // Поля для ввода данных
        RoundedCornerTextField(
            text = lastName,
            label = "Last Name",
            icon = painterResource(id = R.drawable.ic_person),
            onValueChange = { editAccountScreenViewModel.onLastNameChange(it) }
        )

        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextField(
            text = firstName,
            label = "First Name",
            icon = painterResource(id = R.drawable.ic_person),
            onValueChange = { editAccountScreenViewModel.onFirstNameChange(it) }
        )

        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextField(
            text = middleName,
            label = "Middle Name",
            icon = painterResource(id = R.drawable.ic_person),
            onValueChange = { editAccountScreenViewModel.onMiddleNameChange(it) }
        )

        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextField(
            text = phoneNumber,
            label = "Phone Number",
            icon = painterResource(id = R.drawable.ic_person),
            onValueChange = { editAccountScreenViewModel.onPhoneNumberChange(it) }
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = Color.Red,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonColorPart1
            ), onClick = {
                editAccountScreenViewModel.saveUserData() },
        ) {
            Text(text = "Save Changes")
        }
    }
}
