package com.example.fitnessclub.data.widgets

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnessclub.data.Model.Data.Membership
import com.example.fitnessclub.ui.theme.ButtonColorPart1

@Composable
fun MembershipListItem(
    membership: Membership,
    userMembership: Membership?,
    fetchImageBase64: (String, (String) -> Unit, (String) -> Unit) -> Unit,
    onSelectMembership: (String) -> Unit
) {
    val imageBase64 = remember { mutableStateOf<String?>(null) }
    val error = remember { mutableStateOf("") }

    LaunchedEffect(membership.imageID) {
        fetchImageBase64(
            membership.imageID,
            { base64 ->
                imageBase64.value = base64
            },
            { errorMessage ->
                error.value = errorMessage
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .shadow(4.dp)
            .padding(16.dp)
    ) {
        imageBase64.value?.let {
            val bitmap = BitmapFactory.decodeByteArray(
                Base64.decode(it, Base64.DEFAULT), 0,
                Base64.decode(it, Base64.DEFAULT).size
            )
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Membership Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = membership.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (userMembership == null || userMembership.price < membership.price){

            var price = membership.price
            if (userMembership != null)
                price -= userMembership.price
            Button(
                onClick = { onSelectMembership(membership.id) },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonColorPart1
                )
            ) {
                Text(text = "Buy for ${price}₽")
            }
        }

    }
}
