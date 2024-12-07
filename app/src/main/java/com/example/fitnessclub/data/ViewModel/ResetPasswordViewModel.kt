package com.example.fitnessclub.data.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigator
import com.example.fitnessclub.data.Model.AuthRepository
import com.example.fitnessclub.data.Model.UserRepository
import com.example.fitnessclub.data.View.Login.LoginScreenObject
import com.google.firebase.ktx.Firebase

class ResetPasswordViewModel(private val _db: Firebase) : ViewModel() {

    private val _userRepository = UserRepository(_db)

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _error = mutableStateOf("")
    val error: State<String> = _error

    private val _successMessage = mutableStateOf<String>("")
    val successMessage: State<String> = _successMessage

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun resetPassword() {
        if (_email.value.isBlank()) {
            _error.value = "Email cannot be empty"
            return
        }

        _userRepository.sendPasswordResetEmail(
            _email.value,
            onSuccess = {message ->
                _error.value = ""
                _successMessage.value = message
            },
            onFailure = {errorMessage ->
                _successMessage.value = ""
                _error.value = errorMessage
            })
    }

    fun navigateToLoginScreen(onNavigate: (LoginScreenObject) -> Unit){
        onNavigate(LoginScreenObject)
    }
}