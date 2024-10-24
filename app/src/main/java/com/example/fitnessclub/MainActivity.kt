package com.example.fitnessclub

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.fitnessclub.data.Model.AuthRepository
import com.example.fitnessclub.data.View.CreateAccountView
import com.example.fitnessclub.data.View.Login.LoginScreenObject
import com.example.fitnessclub.data.View.Login.LoginView
import com.example.fitnessclub.data.View.MainScreen.MainScreenDataObject
import com.example.fitnessclub.data.View.MainScreen.MainScreenView
import com.example.fitnessclub.data.ViewModel.LoginViewModel
import com.example.fitnessclub.data.ViewModel.LoginViewModelFactory
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val authRepository = AuthRepository(Firebase.auth)
            val loginViewModel = LoginViewModel(authRepository)
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = LoginScreenObject){

                composable<LoginScreenObject> {
                    LoginView(loginViewModel = loginViewModel) { navData ->
                        navController.navigate(navData)}
                }

                composable<MainScreenDataObject> { navEntry ->
                    val navData = navEntry.toRoute<MainScreenDataObject>()
                    MainScreenView(navData)
                }

            }

        }

    }
}


