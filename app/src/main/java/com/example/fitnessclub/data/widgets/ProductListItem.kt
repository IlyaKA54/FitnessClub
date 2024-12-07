package com.example.fitnessclub.data.widgets

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnessclub.data.Model.Data.Product
import com.example.fitnessclub.ui.theme.ButtonColorPart1

@Composable
fun ProductListItem(
    product: Product,
    fetchImageBase64: (String, (String) -> Unit, (String) -> Unit) -> Unit,
    onPurchaseClick: (String) -> Unit
) {
    val imageBase64 = remember { mutableStateOf<String?>(null) }
    val error = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(product.imageID) {
        fetchImageBase64(
            product.imageID,
            { base64 -> imageBase64.value = base64 },
            { errorMessage -> error.value = errorMessage }
        )
    }

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (imageBase64.value != null) {
            val bitmap = base64ToBitmap(imageBase64.value!!)
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = product.name,
                modifier = Modifier.size(100.dp)
            )
        } else {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Gray)
            )
        }

        Text(text = product.name, fontWeight = FontWeight.Bold)
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonColorPart1
            ),
            onClick = { onPurchaseClick(product.id) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "${product.price} ₽")
        }

        error.value?.let {
            Text(text = it, color = Color.Red, fontSize = 12.sp)
        }
    }
}

fun base64ToBitmap(base64: String): Bitmap {
    val decodedString = Base64.decode(base64, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
}
