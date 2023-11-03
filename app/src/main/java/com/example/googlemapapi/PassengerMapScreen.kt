package com.example.googlemapapi

import android.location.Geocoder
import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PassengerMap(navController: NavController, departure: String){
    println("hoge")
    var isButtonClicked by remember { mutableStateOf(false) }
    Column {
        TopAppBarSample()
        BottomBar(
            navController,
            isButtonClicked = isButtonClicked,
            setButtonClicked = { isButtonClicked = it }) { innerPadding ->
            if (departure != null) {
                //SearchDepature(departure)
                PassengerRequestScreenUI(departure)
                //PassengerRequestScreenUI()
            }
        }
    }
    LaunchedEffect(isButtonClicked) {
        if (isButtonClicked) {
            // ... 他の初期化など

            launch(Dispatchers.IO) { // バックグラウンドスレッドでの実行
                try {
                    // ... データベース操作
                } catch (e: Exception) {
                    // エラーハンドリング
                }
            }

            // UIスレッドでの状態更新
            isButtonClicked = false
        }
    }
}

@Composable
fun SearchDepature(departure: String) {
    var mapView: MapView? by remember { mutableStateOf(null) }
    var searchedLatLng by remember { mutableStateOf<LatLng?>(null) }

    // 地図の初期化とマーカーの追加
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val newMapView = MapView(context)
            newMapView.onCreate(Bundle())
            newMapView.getMapAsync { googleMap ->
                val initialLatLng = LatLng(37.523611, 139.937778)
                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(initialLatLng, 15f)
                googleMap.moveCamera(cameraUpdate)

                mapView?.context?.let {
                    val lowerLeftLatitude = 37.4
                    val lowerLeftLongitude = 139.7
                    val upperRightLatitude = 37.6
                    val upperRightLongitude = 140.0

                    val addresses = Geocoder(context).getFromLocationName(
                        departure, 1,
                        lowerLeftLatitude, lowerLeftLongitude,
                        upperRightLatitude, upperRightLongitude
                    )
                    val address = addresses?.getOrNull(0)
                    address?.let {
                        searchedLatLng = LatLng(it.latitude, it.longitude)
                        googleMap.addMarker(
                            MarkerOptions().position(searchedLatLng!!).title(departure)
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
