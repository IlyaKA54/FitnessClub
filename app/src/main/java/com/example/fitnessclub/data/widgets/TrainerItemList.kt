package com.example.fitnessclub.data.widgets

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fitnessclub.data.Model.Data.Trainer

@Composable
fun TrainerItemList(
    trainer: Trainer,
    loadImage: (String, (Bitmap?) -> Unit) -> Unit,
    onClic: (Trainer) -> Unit
) {
    var profileImage by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(trainer.profileImageId) {
        trainer.profileImageId?.let { imageId ->
            loadImage(imageId) { bitmap ->
                profileImage = bitmap
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { onClic(trainer) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            profileImage?.let {
                Image(
                    painter = BitmapPainter(it.asImageBitmap()),
                    contentDescription = "Trainer Image",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "${trainer.lastName} ${trainer.firstName} ${trainer.middleName}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
