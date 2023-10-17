package com.example.googlemapapi

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException
import java.util.UUID

@Composable
fun AnotherScreen(navController: NavController){
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
        if (isButtonClicked) {
            val dataBase = DataBase() // データベースクラスのインスタンスを作成

            val nameValue = name.text
            val departureValue = departure.text
            val destinationValue = destination.text
            val departureTimeValue = departureTime.text
            val capacityValue = capacity.text

            // GraphQL ミューテーションを非同期に実行
            try {
                dataBase.executeMutation(
                    uuid = UUID.randomUUID().toString(),
                    name = nameValue,
                    departure = departureValue,
                    destination = destinationValue,
                    time = departureTimeValue,
                    capacity = capacityValue.toInt(),
                )
                println("hoge");
            } catch (e: Exception) {
                // エラーハンドリング
                e.printStackTrace()
            } finally {
                isButtonClicked = false
            }
        }
    }
}