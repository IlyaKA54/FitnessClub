package com.example.fitnessclub.data.View

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.fitnessclub.data.widgets.RoundedCornerTextField
import com.example.fitnessclub.data.widgets.RoundedCornerTextFieldPassword
import com.example.fitnessclub.ui.theme.ButtonColorPart1
import com.example.fitnessclub.ui.theme.Purple
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun CreateAccountView() {
    val auth = remember { Firebase.auth }

    val emailState = remember {
        mutableStateOf("")
    }
    val passwordState = remember {
        mutableStateOf("")
    }
    val repeatPasswordState = remember {
        mutableStateOf("")
    }

    val errorState = remember {
        mutableStateOf("")
    }

    var checkedState by remember { mutableStateOf(false) }


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
            text = "Create an account",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(30.dp))

        RoundedCornerTextField(
            text = emailState.value,
            label = "Email",
            icon = painterResource(id = R.drawable.ic_mail)
        ) {
            emailState.value = it
        }

        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextFieldPassword(
            text = passwordState.value,
            label = "Password",
            icon = painterResource(id = R.drawable.ic_lock)
        ) {
            passwordState.value = it
        }

        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextFieldPassword(
            text = repeatPasswordState.value,
            label = "Repeat password",
            icon = painterResource(id = R.drawable.ic_lock)
        ) {
            repeatPasswordState.value = it
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Checkbox(
                checked = checkedState,
                onCheckedChange = { checkedState = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = ButtonColorPart1,
                    uncheckedColor = Color.Black,
                    checkmarkColor = Color.White
                )
            )
            Text(
                text = "By continuing you accept our Privacy Policy and Term of Use",
                color = Color.Gray //
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (errorState.value.isNotEmpty()) {
            Text(
                text = errorState.value,
                color = Color.Red,
                textAlign = TextAlign.Center,
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
            ),
            onClick = {
                signUp(
                    auth,
                    emailState.value,
                    passwordState.value,
                    onSignInSuccess = {
                        Log.d("MyLog", "Ok")
                    },
                    onSignInFailure = { error ->
                        errorState.value = error
                    }
                )
            }) {
            Text(text = "Sign up")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = buildAnnotatedString {
                append("Already have an account? ")
                withStyle(
                    style = SpanStyle(
                        color = Purple,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("Login") // Слово "Login"
                }
            },
            modifier = Modifier.clickable {
                println("Hello, World!")
            }
        )
    }
}

fun signUp(
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
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) onSignInSuccess()
        }
        .addOnFailureListener {
            onSignInFailure(it.message ?: "Sign In Error")
        }
}