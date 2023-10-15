package com.example.googlemapapi

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun AnotherScreen(navController: NavController){
    Column {
        TopAppBarSample()
        BottomBar(navController)
    }
}