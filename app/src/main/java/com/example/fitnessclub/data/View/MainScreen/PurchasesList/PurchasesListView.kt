package com.example.fitnessclub.data.View.MainScreen.PurchasesList

import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fitnessclub.data.Model.Data.Product
import com.example.fitnessclub.data.Model.Data.Purchase
import com.example.fitnessclub.data.ViewModel.PurchasesListViewModel
import java.util.Date
import java.util.Locale

@Composable
fun PurchasesListView(viewModel: PurchasesListViewModel) {
    val purchases by viewModel.purchases.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .padding(16.dp)
        ) {
            Text(
                text = "Purchase history",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.Center)
            )
            IconButton(
                onClick = { viewModel.navigateBack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
        }

        LazyColumn {
            items(purchases) { (purchase, product) ->
                PurchaseItem(purchase, product, viewModel)
            }
        }
    }
}

@Composable
fun PurchaseItem(purchase: Purchase, product: Product, viewModel: PurchasesListViewModel) {
    val imageBitmap = remember { mutableStateOf<Bitmap?>(null) }

    // Загрузка изображения
    LaunchedEffect(product.imageID) {
        viewModel.loadImage(product.imageID) { bitmap ->
            imageBitmap.value = bitmap
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        // Product Image
        imageBitmap.value?.let { image ->
            Image(
                bitmap = image.asImageBitmap(),
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )
        } ?: Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = "Default Product Icon",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Product Info
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Purchased on: ${formatDate(purchase.time)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Product Price
        Text(
            text = "${product.price} ₽",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

fun formatDate(timestamp: Long): String {
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return formatter.format(Date(timestamp))
}
