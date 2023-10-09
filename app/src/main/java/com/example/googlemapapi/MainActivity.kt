package com.example.googlemapapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoogleMapScreen()
        }
    }
}

@Composable
fun GoogleMapScreen() {
    var mapView: MapView? by remember { mutableStateOf(null) }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val newMapView = MapView(context)
            newMapView.onCreate(Bundle())
            newMapView.getMapAsync(OnMapReadyCallback { googleMap ->
                // Google Map is ready
            })
            mapView = newMapView
            newMapView
        },
        update = {
            // Handle MapView's lifecycle events
            it?.let { mapView ->
                mapView.onResume()
                mapView.getMapAsync { googleMap ->
                    // Google Map is ready
                }
            }
        }
    )
}
