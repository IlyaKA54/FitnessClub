package com.example.fitnessclub.data.ViewModel

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessclub.data.Model.Data.User
import com.example.fitnessclub.data.Model.ImageManager
import com.example.fitnessclub.data.Model.UserRepository
import com.example.fitnessclub.data.View.MainScreen.MainScreenDataObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditAccountScreenViewModel(
    private val _db: Firebase,
    private val _navData: MainScreenDataObject,
    private val onNavigateBack: () -> Unit
) : ViewModel() {

    private val _userRepository = UserRepository(_db)
    private val _imageManager = ImageManager()

    // Стейт для хранения данных пользователя
    private val _lastName = mutableStateOf("")
    val lastName: State<String> = _lastName

    private val _firstName = mutableStateOf("")
    val firstName: State<String> = _firstName

    private val _middleName = mutableStateOf("")
    val middleName: State<String> = _middleName

    private val _phoneNumber = mutableStateOf("")
    val phoneNumber: State<String> = _phoneNumber

    private val _profileImageUrl = mutableStateOf<String?>(null)
    val profileImageUrl: State<String?> = _profileImageUrl

    private val _profileImageBitmap = mutableStateOf<Bitmap?>(null)
    val profileImageBitmap: State<Bitmap?> = _profileImageBitmap

    private val _membershipId = mutableStateOf<String?>(null)

    private val _error = mutableStateOf("")
    val error: State<String> = _error

    init {
        loadUserData()
    }

    private fun loadUserData() {
        _userRepository.getUserById(_navData.uid, onSuccess = { user ->
            Log.e("EditAccountScreenViewModel", "200")
            _lastName.value = user?.lastName ?: ""
            _firstName.value = user?.firstName ?: ""
            _middleName.value = user?.middleName ?: ""
            _phoneNumber.value = user?.phoneNumber ?: ""
            _profileImageUrl.value = user?.profileImageUrl
            _profileImageBitmap.value = _imageManager.base64ToBitmap(user?.profileImageUrl)
            _membershipId.value = user?.membershipId
        }, onFailure = { exception ->
            Log.e("EditAccountScreenViewModel", "Failed to load user data: ${exception.message}")
        })
    }

    fun onLastNameChange(newLastName: String) {
        _lastName.value = newLastName
    }

    fun onFirstNameChange(newFirstName: String) {
        _firstName.value = newFirstName
    }

    fun onMiddleNameChange(newMiddleName: String) {
        _middleName.value = newMiddleName
    }

    fun onPhoneNumberChange(newPhoneNumber: String) {
        _phoneNumber.value = newPhoneNumber
    }

    fun saveUserData() {
        if (_lastName.value.isBlank() || _firstName.value.isBlank() || _middleName.value.isBlank()
        ) {
            _error.value = "The required fields must be filled in."
            return
        }

        if (_phoneNumber.value.isNotBlank()) {
            val phoneNumberRegex = "^\\d+$".toRegex()
            
            if (_phoneNumber.value.length < 10 || !_phoneNumber.value.matches(phoneNumberRegex)) {
                _error.value = "Phone number must be at least 10 digits and contain only numbers"
                return
            }
        }

        val updatedUser = User(
            lastName = _lastName.value,
            firstName = _firstName.value,
            middleName = _middleName.value,
            phoneNumber = _phoneNumber.value,
            profileImageUrl = _profileImageUrl.value,
            membershipId = _membershipId.value
        )

        saveUserToDb(updatedUser)
    }

    private fun saveUserToDb(user: User) {
        _userRepository.updateUser(_navData.uid, user, onSuccess = {
            onNavigateBack()
        }, onFailure = { exception ->
             _error.value = "Failed to update user: ${exception.message}"
        })
    }

    fun setSelectedImageUri(uri: Uri, contentResolver: ContentResolver) {
        _profileImageUrl.value = _imageManager.imageToBase64(uri, contentResolver)

        viewModelScope.launch(Dispatchers.IO) {
            val bitmap = _imageManager.uriToBitmap(uri, contentResolver)
            _profileImageBitmap.value = bitmap
        }
    }

    fun navigateBack(){
        onNavigateBack()
    }
}
