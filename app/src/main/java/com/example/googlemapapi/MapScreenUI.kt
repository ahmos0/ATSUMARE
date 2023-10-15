package com.example.googlemapapi

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.WhitePoint
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarSample() {
    TopAppBar(
        title = {
            Text(
                text = "ATUMARE",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar(navController: NavController,modifier: Modifier = Modifier) {
    val carImage: Painter = painterResource(R.drawable.directions_car_24px)
    val peopleImage: Painter = painterResource(R.drawable.emoji_people_24px)
    val ImageSizeModifier = Modifier
        .size(70.dp)
    //val ButtonPositionModifier = Modifier.offset(y = 0.dp)

    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = modifier
                    .height(72.dp)
                    .fillMaxWidth(),
                containerColor = Color.White,
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {navController.navigate("anotherScreen") }) {
                        Icon(carImage, contentDescription = null, modifier = ImageSizeModifier)
                    }
                    FloatingActionButton(onClick = { /*TODO*/ },
                        shape = androidx.compose.foundation.shape.CircleShape ){
                        Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            peopleImage,
                            contentDescription = "Localized description",
                            modifier = ImageSizeModifier
                        )
                    }
                }
            }
        },
    ) { innerPadding ->
        GoogleMapScreen(modifier = Modifier.padding(innerPadding))
    }
}
