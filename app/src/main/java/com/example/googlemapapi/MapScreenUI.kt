package com.example.googlemapapi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarSample() {
    TopAppBar(
        title = {
            Text(
                text = "ATUMARE",
                modifier = Modifier.fillMaxWidth() .offset(x = (-10).dp),
                textAlign = TextAlign.Center
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar(navController: NavController, modifier: Modifier = Modifier) {
    val carImage: Painter = painterResource(R.drawable.directions_car_24px)
    val peopleImage: Painter = painterResource(R.drawable.emoji_people_24px)
    val buttonImage: Painter = painterResource(R.drawable.radio_button_checked_24px)
    val ImageSizeModifier = Modifier.size(70.dp)

    Scaffold(
        bottomBar = {
            Box {
                BottomBarContent(
                    carImage = carImage,
                    peopleImage = peopleImage,
                    ImageSizeModifier = ImageSizeModifier,
                    navController = navController
                )
                FloatingActionOverlayButton(
                    buttonImage = buttonImage,
                )
            }
        }
    ) { innerPadding ->
        GoogleMapScreen(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun BottomBarContent(
    carImage: Painter,
    peopleImage: Painter,
    ImageSizeModifier: Modifier,
    navController: NavController
) {
    BottomAppBar(
        modifier = Modifier
            .height(72.dp)
            .fillMaxWidth()
            .offset(y = 623.dp),
        containerColor = Color.White,
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .offset(x = 10.dp), //アイコン二つを右にずらす
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                IconButton(onClick = { navController.navigate("anotherScreen") }) {
                    Icon(carImage, contentDescription = null, modifier = ImageSizeModifier)
                }
                Text(text = "ATUMERU", modifier = Modifier .offset(x = -7.dp))
            }
            Column {
                IconButton(onClick = { navController.navigate("JoinScreen") }) {
                    Icon(
                        peopleImage,
                        contentDescription = "Localized description",
                        modifier = ImageSizeModifier
                    )
                }
                Text(text = "ATUMARU", modifier = Modifier .offset(x = -7.dp))
            }
        }
    }
}
@Composable
fun FloatingActionOverlayButton(
    buttonImage: Painter,
) {
    Box(
        modifier = Modifier.fillMaxSize(), // 必要に応じて修正してください
        contentAlignment = Alignment.CenterEnd
    ) {
        FloatingActionButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .offset(y = 275.dp, x = (-157).dp)
                .size(75.dp),
            shape = CircleShape,
            containerColor = Color(228, 98, 102)
        ) {
            Icon(buttonImage, contentDescription = null, Modifier.size(45.dp))
        }
    }
}