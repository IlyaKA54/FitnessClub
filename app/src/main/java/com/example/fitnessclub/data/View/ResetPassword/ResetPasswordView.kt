package com.example.fitnessclub.data.View.ResetPassword

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnessclub.R
import com.example.fitnessclub.data.View.Login.LoginScreenObject
import com.example.fitnessclub.data.ViewModel.ResetPasswordViewModel
import com.example.fitnessclub.data.widgets.RoundedCornerTextField
import com.example.fitnessclub.ui.theme.ButtonColorPart1
import com.example.fitnessclub.ui.theme.Purple

@Composable
fun ResetPasswordView(
    resetPasswordViewModel: ResetPasswordViewModel,
    onNavigate: (LoginScreenObject) -> Unit
) {
    val emailState by resetPasswordViewModel.email
    val errorState by resetPasswordViewModel.error
    val successState by resetPasswordViewModel.successMessage

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            IconButton(
                onClick = { resetPasswordViewModel.navigateToLoginScreen(onNavigate) },
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

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Reset Password",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(20.dp))

            RoundedCornerTextField(
                text = emailState,
                label = "Enter your email",
                icon = painterResource(id = R.drawable.ic_mail)
            ) {
                resetPasswordViewModel.onEmailChange(it)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Центрирование текста для errorState и successState
            if (errorState.isNotEmpty()) {
                Text(
                    text = errorState,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                )
            }

            if (successState.isNotEmpty()) {
                Text(
                    text = successState,
                    color = Color.Green,
                    textAlign = TextAlign.Center
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonColorPart1
                ),
                onClick = {
                    resetPasswordViewModel.resetPassword()
                }
            ) {
                Text(text = "Send Reset Link")
            }
        }
    }
}
