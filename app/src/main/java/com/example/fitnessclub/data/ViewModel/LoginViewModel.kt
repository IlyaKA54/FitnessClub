package com.example.fitnessclub.data.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessclub.data.Model.AuthRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import com.example.fitnessclub.data.View.MainScreen.MainScreenDataObject

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _error = mutableStateOf("")
    val error: State<String> = _error

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun signIn(onSuccess: (MainScreenDataObject) -> Unit) {

        if (_email.value.isBlank() || _password.value.isBlank()) {
            _error.value = "Email and password cannot be empty"
            return
        }

        authRepository.signIn(
            email = _email.value,
            password = _password.value,
            onSuccess = { navData ->
                onSuccess(navData)
            },
            onFailure = { errorMessage ->
                _error.value = errorMessage
            }
        )
    }
}