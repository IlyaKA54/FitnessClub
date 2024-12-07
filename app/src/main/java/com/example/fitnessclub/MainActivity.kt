package com.example.fitnessclub

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.fitnessclub.data.Model.AuthRepository
import com.example.fitnessclub.data.Model.Data.Activity
import com.example.fitnessclub.data.Model.Data.Trainer
import com.example.fitnessclub.data.Model.UserRepository
import com.example.fitnessclub.data.View.CreateAccount.CreateAccountObject
import com.example.fitnessclub.data.View.CreateAccount.CreateAccountView
import com.example.fitnessclub.data.View.Login.LoginScreenObject
import com.example.fitnessclub.data.View.Login.LoginView
import com.example.fitnessclub.data.View.MainScreen.MainScreenDataObject
import com.example.fitnessclub.data.View.MainScreen.MainScreenView
import com.example.fitnessclub.data.View.ResetPassword.ResetPasswordObject
import com.example.fitnessclub.data.View.ResetPassword.ResetPasswordView
import com.example.fitnessclub.data.ViewModel.CreateAccountViewModel
import com.example.fitnessclub.data.ViewModel.LoginViewModel
import com.example.fitnessclub.data.ViewModel.MainScreenViewModel
import com.example.fitnessclub.data.ViewModel.ResetPasswordViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val _db = Firebase
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = LoginScreenObject){

                composable<LoginScreenObject> {
                    val loginViewModel = LoginViewModel(_db)
                    LoginView(
                        loginViewModel = loginViewModel,
                        onLoginSuccess = { navData ->
                            navController.navigate(navData)},
                        onNavigateToCreateAccountScreen = { navData ->
                            navController.navigate(navData)},
                        onNavigateToResetPasswordScreen = { navData ->
                            navController.navigate(navData)},
                        )
                }

                composable<CreateAccountObject> {
                    val createAccountViewModel = CreateAccountViewModel(_db)
                    CreateAccountView(
                        createAccountViewModel = createAccountViewModel,
                        onCreateUserSuccess = { navData ->
                            navController.navigate(navData)},
                        onNavigate = { navData ->
                            navController.navigate(navData)},
                    )
                }

                composable<ResetPasswordObject> {
                    val resetPasswordViewModel = ResetPasswordViewModel(_db)
                    ResetPasswordView(
                        resetPasswordViewModel = resetPasswordViewModel,
                        onNavigate = { navData ->
                            navController.navigate(navData)},
                    )
                }

                composable<MainScreenDataObject> { navEntry ->
                    val navData = navEntry.toRoute<MainScreenDataObject>()
                    val mainScreenViewModel = MainScreenViewModel(_db, navData)
                    MainScreenView(mainScreenViewModel)
                }

            }

        }

    }
}


