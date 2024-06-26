package com.example.googlemapapi

import android.util.Log
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdjustPassengerRequestScreenUI(
                                   departure: String,
                                   isButtonClicked: Boolean,
                                   namePassenger: String,
                                   request: String,
                                   onNameChange: (String) -> Unit,
                                   onRequestChange: (String) -> Unit) {
    var nameState by remember { mutableStateOf(TextFieldValue(namePassenger)) }
    var requestState by remember { mutableStateOf(TextFieldValue(request)) }
    Box {
        SearchDepature(departure)
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val width = size.width
            val height = size.height
            drawRect(
                color = Color(230, 230, 230),
                topLeft = Offset(x = 0f,y = height/2),
                size = Size(width, height/2),
            )
        }
        Column(
            modifier = Modifier
                .offset(y = 100.dp)
                .fillMaxSize()
                .padding(35.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = nameState,
                onValueChange = {
                    Log.d("AdjustPassengerRequestScreenUI", "Name input: ${it.text}")
                    nameState = it
                    onNameChange(it.text) // 変更された値をコールバックを通じて外部に通知
                },
                label = { Text("名前") },
                modifier = Modifier
                    .width(100.dp)
                    .height(60.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                ),
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = requestState,
                onValueChange = {
                    Log.d("AdjustPassengerRequestScreenUI", "Request input: ${it.text}")
                    requestState = it
                    onRequestChange(it.text) // 変更された値をコールバックを通じて外部に通知
                },
                label = { Text("要望をここに入力") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                singleLine = false,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                ),
            )
        }
    }
}
