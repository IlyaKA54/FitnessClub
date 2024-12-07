package com.example.fitnessclub.data.View.CreateAccount

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitnessclub.R
import com.example.fitnessclub.data.View.Login.LoginScreenObject
import com.example.fitnessclub.data.View.MainScreen.MainScreenDataObject
import com.example.fitnessclub.data.ViewModel.CreateAccountViewModel
import com.example.fitnessclub.data.widgets.RoundedCornerTextField
import com.example.fitnessclub.data.widgets.RoundedCornerTextFieldPassword
import com.example.fitnessclub.ui.theme.ButtonColorPart1
import com.example.fitnessclub.ui.theme.Purple
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun CreateAccountView(
    createAccountViewModel: CreateAccountViewModel,
    onCreateUserSuccess: (MainScreenDataObject) -> Unit,
    onNavigate: (LoginScreenObject) -> Unit
) {

    val emailState by createAccountViewModel.email
    val passwordState by createAccountViewModel.password
    val repeatPasswordState by createAccountViewModel.repeatPassword
    val errorState by createAccountViewModel.error
    val checkedState by createAccountViewModel.checkedState


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
            text = emailState,
            label = "Email",
            icon = painterResource(id = R.drawable.ic_mail),
            onValueChange = { createAccountViewModel.onEmailChange(it) }
        )

        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextFieldPassword(
            text = passwordState,
            label = "Password",
            icon = painterResource(id = R.drawable.ic_lock),
            onValueChange = { createAccountViewModel.onPasswordChange(it) }
        )

        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextFieldPassword(
            text = repeatPasswordState,
            label = "Repeat password",
            icon = painterResource(id = R.drawable.ic_lock),
            onValueChange = {createAccountViewModel.onRepeatPasswordChange(it)}
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Checkbox(
                checked = checkedState,
                onCheckedChange = { createAccountViewModel.onCheckedStateChange(it) },
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

        if (errorState.isNotEmpty()) {
            Text(
                text = errorState,
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
                createAccountViewModel.signUp(onCreateUserSuccess)
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
                    append("Login")
                }
            },
            modifier = Modifier.clickable {
                createAccountViewModel.navigateToLoginScreen(onNavigate)
            }
        )
    }
}

