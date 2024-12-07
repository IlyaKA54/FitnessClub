package com.example.fitnessclub.data.View.MainScreen.MembershipController

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.fitnessclub.data.ViewModel.MembershipControllerViewModel
import com.example.fitnessclub.data.widgets.MembershipListItem

@Composable
fun MembershipControllerView(
    membershipControllerViewModel: MembershipControllerViewModel
) {
    val memberships by membershipControllerViewModel.memberships
    val error by membershipControllerViewModel.error
    val userMembership = membershipControllerViewModel.userMembership.value

    Column(modifier = Modifier.fillMaxSize()) {
        IconButton(
            onClick = { membershipControllerViewModel.navigateBack() },
            modifier = Modifier
                .align(Alignment.Start)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Gray,
                modifier = Modifier.size(50.dp)
            )
        }

        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = Color.Red,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        LazyColumn(
            contentPadding = PaddingValues(bottom = 74.dp) // Добавляем отступ снизу
        ) {
            items(memberships) { membership ->
                MembershipListItem(
                    membership = membership,
                    userMembership = userMembership,
                    fetchImageBase64 = membershipControllerViewModel::fetchImageBase64,
                    onSelectMembership = { membershipId ->
                        membershipControllerViewModel.selectMembership(membershipId) {
                            membershipControllerViewModel.navigateBack()
                        }
                    }
                )
            }
        }
    }
}
