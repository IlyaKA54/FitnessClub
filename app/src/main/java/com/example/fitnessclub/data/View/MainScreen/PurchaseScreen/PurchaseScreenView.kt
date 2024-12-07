package com.example.fitnessclub.data.View.MainScreen.PurchaseScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnessclub.data.Model.Data.Purchase
import com.example.fitnessclub.data.ViewModel.PurchaseScreenViewModel
import com.example.fitnessclub.data.widgets.ProductListItem
import java.time.format.DateTimeFormatter

@Composable
fun PurchaseScreenView(
    purchaseScreenViewModel: PurchaseScreenViewModel,
    onNavigate: (String) -> Unit
) {
    val products by purchaseScreenViewModel.products
    val error by purchaseScreenViewModel.error

    Column( modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .padding(28.dp)
        ) {
            Text(
                text = "Purchase",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )

        }

        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            items(products) { product ->
                ProductListItem(
                    product = product,
                    fetchImageBase64 = { imageID, onSuccess, onFailure ->
                        purchaseScreenViewModel.fetchImageBase64(imageID, onSuccess, onFailure)
                    },
                    onPurchaseClick = { productId ->
                        purchaseScreenViewModel.purchaseProduct(productId) {
                            Log.d("PurchaseScreenView", "Purchase was successful ")
                        }
                    }
                )
            }
        }
    }
}
