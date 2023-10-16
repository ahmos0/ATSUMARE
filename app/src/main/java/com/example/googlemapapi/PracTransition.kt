package com.example.googlemapapi

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import aws.sdk.kotlin.services.dynamodb.DynamoDbClient

@Composable
fun AnotherScreen(navController: NavController){
    /*Column {
        TopAppBarSample()
        BottomBar(navController)
    }*/
    RegistrationScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen() {
    var name by remember { mutableStateOf(TextFieldValue()) }
    var departure by remember { mutableStateOf(TextFieldValue()) }
    var destination by remember { mutableStateOf(TextFieldValue()) }
    var departureTime by remember { mutableStateOf(TextFieldValue()) }
    var capacity by remember { mutableStateOf(TextFieldValue()) }

    var isButtonClicked by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("名前") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = departure,
            onValueChange = { departure = it },
            label = { Text("出発地") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = destination,
            onValueChange = { destination = it },
            label = { Text("目的地") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = departureTime,
            onValueChange = { departureTime = it },
            label = { Text("出発時刻") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = capacity,
            onValueChange = { capacity = it },
            label = { Text("定員") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            isButtonClicked = true
        }) {
            Text("登録")
        }
    }
    //DB処理をボタンを押されたらするように
    LaunchedEffect(isButtonClicked) {
        if(isButtonClicked) {
            val ddb = DynamoDbClient { region = "ap-northeast-1" }
            val database = Database()

            val nameValue = name.text
            val departureValue = departure.text
            val destinationValue = destination.text
            val departureTimeValue = departureTime.text
            val capacityValue = capacity.text

            try {
                database.putItemInTable(
                    ddb = ddb,
                    tableNameVal = "DepatureManageTable",
                    name = nameValue,
                    departurePoint = departureValue,
                    destination = destinationValue,
                    departureTime = departureTimeValue,
                    capacity = capacityValue
                )
            } catch (e: Exception) {
                // ここでエラーを適切に処理またはロギング
                println("Error while inserting data to DynamoDB: ${e.message}")
            } finally {
                // 必要に応じてDynamoDbClientをクローズ
                ddb.close()
            }

            isButtonClicked = false
        }
    }



}
