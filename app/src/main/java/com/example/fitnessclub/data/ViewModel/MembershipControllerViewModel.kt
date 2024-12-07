package com.example.fitnessclub.data.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.fitnessclub.data.Model.Data.Membership
import com.example.fitnessclub.data.Model.MembershipRepository
import com.example.fitnessclub.data.Model.UserRepository
import com.example.fitnessclub.data.View.MainScreen.MainScreenDataObject
import com.google.firebase.ktx.Firebase

class MembershipControllerViewModel(
    private val _db: Firebase,
    private val _navData: MainScreenDataObject,
    private val onNavigateBack: () -> Unit
) : ViewModel() {

    private val _membershipRepository = MembershipRepository(_db)
    private val _userRepository = UserRepository(_db)

    private val _memberships = mutableStateOf<List<Membership>>(emptyList())
    val memberships: State<List<Membership>> = _memberships

    private val _userMembership = mutableStateOf<Membership?>(null)  // Текущий абонемент пользователя
    val userMembership: State<Membership?> = _userMembership

    private val _error = mutableStateOf("")
    val error: State<String> = _error

    init {
        fetchUserMembership()
        fetchMemberships()
    }

    private fun fetchUserMembership() {
        _userRepository.getUserById(_navData.uid, onSuccess = { user ->
            val membershipId = user?.membershipId // Предположим, что в user есть поле membershipId
            if (!membershipId.isNullOrEmpty()) {
                _membershipRepository.getMembershipById(membershipId, onSuccess = { membership ->
                    _userMembership.value = membership // Сохраняем абонемент пользователя
                }, onFailure = { errorMessage ->
                    _error.value = errorMessage
                })
            } else {
                _userMembership.value = null // Если абонемент не найден
            }
        }, onFailure = { errorMessage ->
            _error.value = errorMessage.toString()
        })
    }

    private fun fetchMemberships() {
        _membershipRepository.fetchMemberships(
            onSuccess = { memberships ->
                _memberships.value = memberships
            },
            onFailure = { errorMessage ->
                _error.value = errorMessage
            }
        )
    }

    fun fetchImageBase64(
        imageId: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        _membershipRepository.fetchImageBase64(
            imageId = imageId,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    fun selectMembership(membershipId: String, onSuccess: () -> Unit) {
        _userRepository.updateUserMembership(membershipId,
            onSuccess = {
                onSuccess()
            },
            onFailure = { errorMessage ->
                _error.value = errorMessage
            }
        )
    }

    fun navigateBack() {
        onNavigateBack()
    }
}

