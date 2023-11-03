package com.example.googlemapapi

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassengerRequestScreenUI(departure: String) {
    var name by remember { mutableStateOf(TextFieldValue()) }
    var request by remember { mutableStateOf(TextFieldValue()) }
    Box {
        SearchDepature(departure)
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val width = size.width
            val height = size.height
            drawRoundRect(
                color = Color(230, 230, 230),
                topLeft = Offset(60f, 1700f),
                size = Size(width - 2 * 60f, height - 2 * 1200f + 100f),
                cornerRadius = CornerRadius(x = 25F, y = 25F)
            )
        }
        Column(
            modifier = Modifier
                .offset(y = 200.dp)
                .fillMaxSize()
                .padding(35.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(2.dp))
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("名前") },
                modifier = Modifier
                    .width(100.dp)
                    .height(45.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                ),
            )
            Spacer(modifier = Modifier.height(5.dp))
            TextField(
                value = request,
                onValueChange = { request = it },
                label = { Text("要望をここに入力") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                ),
            )
        }
    }
}
