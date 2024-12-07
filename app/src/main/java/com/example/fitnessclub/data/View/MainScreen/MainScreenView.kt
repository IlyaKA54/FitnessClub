package com.example.fitnessclub.data.View.MainScreen

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fitnessclub.data.Test.ScheduleScreen
import com.example.fitnessclub.data.View.MainScreen.ActivitiesLIstScreen.ActivitiesListScreenView
import com.example.fitnessclub.data.View.MainScreen.EditAccountScreen.EditAccountScreenView
import com.example.fitnessclub.data.View.MainScreen.MembershipController.MembershipControllerView
import com.example.fitnessclub.data.View.MainScreen.PurchaseScreen.PurchaseScreenView
import com.example.fitnessclub.data.View.MainScreen.PurchasesList.PurchasesListView
import com.example.fitnessclub.data.View.MainScreen.Schedule.DatePickerScreenView
import com.example.fitnessclub.data.View.MainScreen.Schedule.ShowActivityInfoView
import com.example.fitnessclub.data.View.MainScreen.TrainerInfo.TrainerInfoView
import com.example.fitnessclub.data.View.MainScreen.TrainersList.TrainersListView
import com.example.fitnessclub.data.View.MainScreen.UserScreen.AccountScreenView
import com.example.fitnessclub.data.View.MainScreen.UserScreen.ProfileDataObject
import com.example.fitnessclub.data.ViewModel.EditAccountScreenViewModel
import com.example.fitnessclub.data.ViewModel.MainScreenViewModel
import com.example.fitnessclub.data.widgets.BottomMenu
import com.example.fitnessclub.data.widgets.BottomMenuItem

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreenView(mainScreenViewModel: MainScreenViewModel) {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomMenu(navController)
        }
    ) {

        NavHost(
            navController = navController,
            startDestination = BottomMenuItem.Purchase.route
        ) {
            composable(BottomMenuItem.Purchase.route) {
                mainScreenViewModel.ShowPurchaseScreen { purchaseScreenModel ->
                    PurchaseScreenView(purchaseScreenModel) { str ->
                        navController.navigate(str)
                    }
                }
            }
            composable(BottomMenuItem.Schedule.route) {
                mainScreenViewModel.ShowDatePickerView { datePickerViewModel ->
                    DatePickerScreenView(datePickerViewModel){str, actionId ->
                        mainScreenViewModel.onIdStrChange(actionId)
                        navController.navigate(str)
                    }
                }
            }

            composable(BottomMenuItem.Account.route) {
                mainScreenViewModel.ShowAccountScreen { accountScreenViewModel ->
                    AccountScreenView(accountScreenViewModel) { str ->
                        navController.navigate(str)
                    }
                }
            }

            composable("edit_account_screen") {
                mainScreenViewModel.ShowEditAccountScreenView(onNavigateToBack = { navController.popBackStack() }) {editAccountScreenViewModel ->
                    EditAccountScreenView(editAccountScreenViewModel)
                }
            }

            composable("membership_controller") {
                mainScreenViewModel.ShowMembershipControllerScreenView(onNavigateToBack = { navController.popBackStack() }) {membershipControllerViewModel ->
                    MembershipControllerView(membershipControllerViewModel)
                }
            }

            composable("show_activity") {
                mainScreenViewModel.ShowActivityInfo(onNavigateToBack = { navController.popBackStack() }) {viewModel ->
                    ShowActivityInfoView(viewModel)
                }
            }

            composable("purchases_items") {
                mainScreenViewModel.ShowPurchasesListScreenView (onNavigateToBack = { navController.popBackStack() }) {purchasesListViewModel ->
                    PurchasesListView(purchasesListViewModel)
                }
            }

            composable("booking_items") {
                mainScreenViewModel.ShowActivitiesListScreenView(onNavigateToBack = { navController.popBackStack() }) {activitiesListScreenViewModel ->
                    ActivitiesListScreenView(activitiesListScreenViewModel)
                }
            }

            composable("trainers_list") {
                mainScreenViewModel.ShowTrainersListScreenView(onNavigateToBack = { navController.popBackStack() }) {trainersListScreenViewModel ->
                    TrainersListView(trainersListScreenViewModel){str, trainer ->
                        mainScreenViewModel.onTrainerChange(trainer)
                        navController.navigate(str)
                    }
                }
            }

            composable("trainer_info") {
                mainScreenViewModel.ShowTrainersInfo(onNavigateToBack = { navController.popBackStack() }) {trainersInfoViewModel ->
                    TrainerInfoView(trainersInfoViewModel)
                }
            }
        }
    }
}