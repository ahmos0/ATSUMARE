package com.example.googlemapapi

import RegisterInfoUI
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun JoinScreenItem(navController: NavController) {
    var items by remember { mutableStateOf<List<AllItemsQuery.AllItem>?>(null) }
    var updateTrigger by remember { mutableStateOf(0) }

    LaunchedEffect(updateTrigger) {
        val dataBase = DataBase()
        try {
            items = dataBase.fetchAllItems()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(197, 198, 184)),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(items ?: emptyList()) { item ->
            ItemRow(navController, item, onItemClicked = {
                //navController.navigate("PassengerMap/${item.departure}")
                print("hoge")
                //updateTrigger++
            })
        }
    }
}

@Composable
fun ItemRow(navController: NavController, item: AllItemsQuery.AllItem, onItemClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                val uuid = item.uuid ?: throw IllegalArgumentException("UUID is null")
                val name = item.name ?: throw IllegalArgumentException("name is null")
                navController.navigate("PassengerMap/${item.departure}")
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = item.name ?: "N/A")
            Text(text = "${item.departure} -> ${item.destination}")
            Text(text = item.time ?: "N/A")
            Text(text = "${item.passenger}/${item.capacity}")
        }
    }
}

@Composable
fun JoinScreen(navController: NavController) {
    var isButtonClicked by remember { mutableStateOf(false) }

    Column {
        TopAppBarSample()
        BottomBar(
            navController,
            isButtonClicked = isButtonClicked,
            setButtonClicked = { isButtonClicked = it }) { innerPadding ->
            JoinScreenItem(navController)
        }
    }
}