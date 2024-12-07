package com.example.fitnessclub.data.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.fitnessclub.data.Model.AuthRepository
import com.example.fitnessclub.data.View.Login.LoginScreenObject
import com.example.fitnessclub.data.View.MainScreen.MainScreenDataObject
import com.google.firebase.ktx.Firebase

class CreateAccountViewModel(db: Firebase) : ViewModel() {

    private val _authRepository = AuthRepository(db)
    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _repeatPassword = mutableStateOf("")
    val repeatPassword: State<String> = _repeatPassword

    private val _checkedState = mutableStateOf(false)
    val checkedState: State<Boolean> = _checkedState

    private val _error = mutableStateOf("")
    val error: State<String> = _error

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onRepeatPasswordChange(newPassword: String) {
        _repeatPassword.value = newPassword
    }

    fun onCheckedStateChange(newState: Boolean) {
        _checkedState.value = newState
    }

    fun signUp(onSuccess: (MainScreenDataObject) -> Unit) {

        if (_email.value.isBlank() || _password.value.isBlank()) {
            _error.value = "Email and password cannot be empty"
            return
        }

        if (_password.value != _repeatPassword.value) {
            _error.value = "The passwords do not match"
            return
        }

        if (!_checkedState.value){
            _error.value = "You must accept the Privacy Policy and Terms of Use to continue."
            return
        }

        _authRepository.signUp(
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

    fun navigateToLoginScreen(onNavigate: (LoginScreenObject) -> Unit){
        onNavigate(LoginScreenObject)
    }
}