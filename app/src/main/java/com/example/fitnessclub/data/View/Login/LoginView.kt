package com.example.fitnessclub.data.View.Login

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import coil.compose.AsyncImagePainter
import com.example.fitnessclub.R
import com.example.fitnessclub.data.ViewModel.LoginViewModel
import com.example.fitnessclub.data.widgets.RoundedCornerTextField
import com.example.fitnessclub.data.widgets.RoundedCornerTextFieldPassword
import com.example.fitnessclub.ui.theme.ButtonColorPart1
import com.example.fitnessclub.ui.theme.Purple
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitnessclub.data.View.MainScreen.MainScreenDataObject

@Composable
fun LoginView(
    loginViewModel: LoginViewModel = viewModel(),
    onLoginSuccess: (MainScreenDataObject) -> Unit
) {
    val email by loginViewModel.email
    val password by loginViewModel.password
    val error by loginViewModel.error

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Hey there",
            fontSize = 20.sp,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Welcome back",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(30.dp))

        RoundedCornerTextField(
            text = email,
            label = "Email",
            icon = painterResource(id = R.drawable.ic_mail),
            onValueChange = { loginViewModel.onEmailChange(it) }
        )

        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextFieldPassword(
            text = password,
            label = "Password",
            icon = painterResource(id = R.drawable.ic_lock),
            onValueChange = { loginViewModel.onPasswordChange(it) }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Gray,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("Forgot your password?")
                }
            },
            modifier = Modifier.clickable {
                println("Hello, World!")
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = Color.Red
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    )
    {

        Button(
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonColorPart1
            ), onClick = {
                loginViewModel.signIn(onLoginSuccess) },
            ) {
            Text(text = "Sign in")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = buildAnnotatedString {
                append("Don't have an account yet? ")
                withStyle(
                    style = SpanStyle(
                        color = Purple,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("Register")
                }
            },
            modifier = Modifier.clickable {
                println("Hello, World!")
            }
        )
    }
}

fun signIn(
    auth: FirebaseAuth,
    email: String,
    password: String,
    onSignInSuccess: () -> Unit,
    onSignInFailure: (String) -> Unit
) {
    if (email.isBlank() || password.isBlank()) {
        onSignInFailure("Email and password cannot be empty")
        return
    }
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) onSignInSuccess()
        }
        .addOnFailureListener {
            onSignInFailure(it.message ?: "Sign In Error")
        }
}