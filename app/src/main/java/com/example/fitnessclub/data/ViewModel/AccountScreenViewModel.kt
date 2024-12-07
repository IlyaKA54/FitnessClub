package com.example.fitnessclub.data.ViewModel

import android.graphics.Bitmap
import android.util.Log
import android.util.Printer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnessclub.data.Model.Data.User
import com.example.fitnessclub.data.Model.ImageManager
import com.example.fitnessclub.data.Model.UserRepository
import com.example.fitnessclub.data.View.MainScreen.MainScreenDataObject
import com.example.fitnessclub.data.View.MainScreen.UserScreen.ProfileDataObject
import com.google.firebase.ktx.Firebase

class AccountScreenViewModel(
    private val _db: Firebase,
    private val _data: MainScreenDataObject
) : ViewModel() {

    private val _userRepository = UserRepository(_db)
    private val _imageManager = ImageManager()

    private val _username = MutableLiveData<String>()

    private val _profileImageBitmap = mutableStateOf<Bitmap?>(null)
    val profileImageBitmap: State<Bitmap?> = _profileImageBitmap

    val username: LiveData<String> = _username

    init {
        loadUserData()
    }

    private fun loadUserData() {
        _userRepository.getUserById(_data.uid, onSuccess = { user ->
            _username.value = user?.firstName ?: "Пользователь"
            _profileImageBitmap.value = _imageManager.base64ToBitmap(user?.profileImageUrl)
        }, onFailure = { exception ->
            Log.e("AccountScreenViewModel", "Failed to load user data: ${exception.message}")
        })
    }
}