package com.example.googlemapapi

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun JoinScreen(navController: NavController) {
    var items by remember { mutableStateOf<List<AllItemsQuery.AllItem>?>(null) }

    LaunchedEffect(Unit) {
        val dataBase = DataBase()
        try {
            items = dataBase.fetchAllItems()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(items ?: emptyList()) { item ->
            // 各アイテムを表示するUI
            ItemRow(item)
        }
    }
}

@Composable
fun ItemRow(item: AllItemsQuery.AllItem){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { /* アイテムクリック時の処理 */ },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = item.name ?: "N/A")
            Text(text = "${item.departure} -> ${item.destination}")
            Text(text = item.time ?: "N/A")
            Text(text = "${item.capacity} seats")
        }
    }
}