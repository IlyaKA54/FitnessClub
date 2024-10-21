package com.example.fitnessclub

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.fitnessclub.data.Model.AuthRepository
import com.example.fitnessclub.data.View.CreateAccountView
import com.example.fitnessclub.data.View.LoginView
import com.example.fitnessclub.data.ViewModel.LoginViewModel
import com.example.fitnessclub.data.ViewModel.LoginViewModelFactory
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authRepository = AuthRepository(Firebase.auth)

        // Инициализируем ViewModel
        val loginViewModel = ViewModelProvider(this, LoginViewModelFactory(authRepository))
            .get(LoginViewModel::class.java)
        setContent {
            LoginView(loginViewModel = loginViewModel) {
                Log.d("MyLog","Login successful!")
            }
        }
    }
}

