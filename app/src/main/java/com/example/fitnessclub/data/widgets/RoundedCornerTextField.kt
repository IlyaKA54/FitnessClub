package com.example.fitnessclub.data.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.fitnessclub.R
import com.example.fitnessclub.ui.theme.TextBoxBgColorLightGray

@Composable
fun RoundedCornerTextField(
    text: String,
    label: String,
    icon: Painter,
    onValueChange:(String) -> Unit,
) {
    TextField(value = text, onValueChange = {
        onValueChange(it)
    },
        shape = RoundedCornerShape(25.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = TextBoxBgColorLightGray,
            focusedContainerColor = TextBoxBgColorLightGray,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        label = {
            Text(text = label, color = Color.Gray)
        },
        leadingIcon = {
            Icon(
                painter = icon,
                contentDescription = "",
                tint = Color.Gray
            )
        },
        modifier = Modifier.fillMaxWidth(0.8f),
        singleLine = true
    )
}