package com.example.googlemapapi

import android.location.Geocoder
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "main") {
                composable("main") {
                    MainScreen(navController)
                }
                composable("anotherScreen") {
                    AnotherScreen(navController)
                }
                composable("JoinScreen") {
                    JoinScreen(navController)
                }
                composable("Instant") {
                    InstantMap(navController)
                }
            }
        }
    }
}

//最初に呼び出される画面
@Composable
fun MainScreen(navController: NavController) {
    Column {
        TopAppBarSample()
        BottomBar(navController)
    }
}

@Composable
fun GoogleMapScreen(modifier: Modifier = Modifier) {
    var mapView: MapView? by remember { mutableStateOf(null) }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val newMapView = MapView(context)
            newMapView.onCreate(Bundle())
            newMapView.getMapAsync { googleMap ->
                val latLng = LatLng(37.523611, 139.937778)
                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15f)
                googleMap.moveCamera(cameraUpdate)
            }
            mapView = newMapView
            newMapView
        },
        update = {
            // Handle MapView's lifecycle events
            it?.let { mapView ->
                mapView.onResume()
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    TopAppBarSample()
}
