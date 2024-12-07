package com.example.fitnessclub.data.View.MainScreen.Schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.fitnessclub.data.ViewModel.ShowActivityInfoViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.fitnessclub.ui.theme.ButtonColorPart1

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowActivityInfoView(viewModel: ShowActivityInfoViewModel) {
    val activity by viewModel.activityBind.collectAsState()
    val trainer by viewModel.trainer.collectAsState()
    val buttonText by viewModel.buttonText.collectAsState()
    val trainerImage by viewModel.trainerImage.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .padding(16.dp)
        ) {
            Text(
                text = activity?.name ?: "Loading...",
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Date and Time
            activity?.let {
                val startTime = Instant.ofEpochMilli(it.startAt).atZone(ZoneId.systemDefault())
                val endTime = startTime.plusSeconds(it.time)

                Text(
                    text = "${startTime.format(DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm"))} - " +
                            "${endTime.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            trainer?.let { trainer ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically // Центрируем элементы по вертикали
                ) {
                    trainerImage?.let { image ->
                        Image(
                            bitmap = image.asImageBitmap(),
                            contentDescription = "Trainer Image",
                            modifier = Modifier
                                .size(64.dp) // Указываем размер изображения
                                .clip(CircleShape) // Обрезаем изображение в круг
                                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape) // Обводка вокруг изображения
                        )
                    } ?: Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Default Trainer Icon",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(16.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = "${trainer.lastName} ${trainer.firstName}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            // Activity Description
            Text(
                text = activity?.description ?: "No description",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    )
    {

        Button(
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonColorPart1
            ), onClick = { viewModel.bookActivity() },
        ) {
            Text(text = buttonText)
        }
    }


}
