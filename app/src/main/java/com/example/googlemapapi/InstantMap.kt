package com.example.googlemapapi

import android.location.Geocoder
import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


@Composable
fun InstantMap(navController: NavController) {
    InstantMapSearch()
}

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstantMapSearch(modifier: Modifier = Modifier) {
    var mapView: MapView? by remember { mutableStateOf(null) }
    var searchText by remember { mutableStateOf("") }
    var searchedLatLng by remember { mutableStateOf<LatLng?>(null) }

    Column {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("施設名を入力") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Button(onClick = {
            mapView?.context?.let { context ->
                // 会津若松市の範囲
                val lowerLeftLatitude = 37.4
                val lowerLeftLongitude = 139.7
                val upperRightLatitude = 37.6
                val upperRightLongitude = 140.0

                val addresses = Geocoder(context).getFromLocationName(
                    searchText, 1,
                    lowerLeftLatitude, lowerLeftLongitude,
                    upperRightLatitude, upperRightLongitude
                )
                val address = addresses?.getOrNull(0)
                address?.let {
                    searchedLatLng = LatLng(it.latitude, it.longitude)
                    mapView?.getMapAsync { googleMap ->
                        googleMap.clear()
                        googleMap.addMarker(
                            MarkerOptions().position(searchedLatLng!!).title(searchText)
                        )
                        googleMap.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                searchedLatLng!!,
                                15f
                            )
                        )
                    }
                }
            }
        }) {
            Text("検索")
        }



        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                val newMapView = MapView(context)
                newMapView.onCreate(Bundle())
                newMapView.getMapAsync { googleMap ->
                    val initialLatLng = LatLng(37.523611, 139.937778)
                    val cameraUpdate = CameraUpdateFactory.newLatLngZoom(initialLatLng, 15f)
                    googleMap.moveCamera(cameraUpdate)

                    searchedLatLng?.let {
                        googleMap.addMarker(MarkerOptions().position(it).title(searchText))
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 15f))
                    }
                }
                mapView = newMapView
                newMapView
            },
            update = {
                it?.let { mapView ->
                    mapView.onResume()
                }
            }
        )
    }
}
