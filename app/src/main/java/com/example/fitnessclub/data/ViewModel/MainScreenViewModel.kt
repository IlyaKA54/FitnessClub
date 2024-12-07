package com.example.fitnessclub.data.ViewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.fitnessclub.data.Model.Data.Activity
import com.example.fitnessclub.data.Model.Data.Trainer
import com.example.fitnessclub.data.Model.Data.User
import com.example.fitnessclub.data.Model.UserRepository
import com.example.fitnessclub.data.View.MainScreen.MainScreenDataObject
import com.google.firebase.ktx.Firebase

class MainScreenViewModel(
    private val _db: Firebase,
    private val _navData: MainScreenDataObject) : ViewModel() {

    private val userRepository = UserRepository(_db)

    private val _touchId = mutableStateOf<Activity?>(null)
    private val _touchTrainer = mutableStateOf<Trainer?>(null)

    fun onIdStrChange(newStr: Activity) {
        _touchId.value = newStr
    }

    fun onTrainerChange(trainer: Trainer) {
        _touchTrainer.value = trainer
    }

    init {
        checkOrCreateUser(_navData.uid)
    }

    private fun checkOrCreateUser(uid: String) {

        userRepository.checkIfUserExists(uid, { existingUser ->
            if (existingUser) {
                Log.e("MainScreenViewModel" ,"Log in is successful")
            } else {
                val newUser = User()
                userRepository.createUser(uid, newUser, {
                    Log.e("MainScreenViewModel" ,"New user: ${uid} was created")
                }, { exception ->
                    Log.e("MainScreenViewModel" ,"Failed to create user: ${exception.message}")
                })
            }
        }, { exception ->
            Log.e("MainScreenViewModel" ,"Failed to fetch user: ${exception.message}")
        })
    }

    @Composable
    fun ShowAccountScreen(onNavigate: @Composable (AccountScreenViewModel) -> Unit) {
        val accountScreenViewModel = AccountScreenViewModel(_db, _navData)
        onNavigate(accountScreenViewModel)
    }

    @Composable
    fun ShowPurchaseScreen(onNavigate: @Composable (PurchaseScreenViewModel) -> Unit) {
        val purchaseScreenViewModel = PurchaseScreenViewModel(_db, _navData)
        onNavigate(purchaseScreenViewModel)
    }

    @Composable
    fun ShowEditAccountScreenView(onNavigateToBack: () -> Unit ,onNavigate: @Composable (EditAccountScreenViewModel) -> Unit) {
        val editAccountScreenViewModel = EditAccountScreenViewModel(_db, _navData, onNavigateToBack)
        onNavigate(editAccountScreenViewModel)
    }

    @Composable
    fun ShowMembershipControllerScreenView(onNavigateToBack: () -> Unit ,onNavigate: @Composable (MembershipControllerViewModel) -> Unit) {
        val membershipControllerViewModel = MembershipControllerViewModel(_db, _navData, onNavigateToBack)
        onNavigate(membershipControllerViewModel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun ShowDatePickerView(onNavigate: @Composable (DatePickerViewModel) -> Unit) {
        val datePickerViewModel = DatePickerViewModel(_db)
        onNavigate(datePickerViewModel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun ShowActivityInfo(onNavigateToBack: () -> Unit, onNavigate: @Composable (ShowActivityInfoViewModel) -> Unit){
        val activityScreenViewModel = ShowActivityInfoViewModel(_db, _navData, onNavigateToBack, _touchId.value)
        onNavigate(activityScreenViewModel)
    }

    @Composable
    fun ShowPurchasesListScreenView(onNavigateToBack: () -> Unit ,onNavigate: @Composable (PurchasesListViewModel) -> Unit) {
        val purchasesListViewModel = PurchasesListViewModel(_db, _navData, onNavigateToBack)
        onNavigate(purchasesListViewModel)
    }

    @Composable
    fun ShowActivitiesListScreenView(onNavigateToBack: () -> Unit ,onNavigate: @Composable (ActivitiesListScreenViewModel) -> Unit) {
        val activitiesListScreenViewModel = ActivitiesListScreenViewModel(_db, _navData, onNavigateToBack)
        onNavigate(activitiesListScreenViewModel)
    }

    @Composable
    fun ShowTrainersListScreenView(onNavigateToBack: () -> Unit ,onNavigate: @Composable (TrainersListViewModel) -> Unit) {
        val trainersListViewModel = TrainersListViewModel(_db, onNavigateToBack)
        onNavigate(trainersListViewModel)
    }

    @Composable
    fun ShowTrainersInfo(onNavigateToBack: () -> Unit ,onNavigate: @Composable (TrainerInfoViewModel) -> Unit) {
        val trainerInfoViewModel = TrainerInfoViewModel(_db,_navData, _touchTrainer.value ,onNavigateToBack)
        onNavigate(trainerInfoViewModel)
    }

}