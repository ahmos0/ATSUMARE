package com.example.googlemapapi

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
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
import kotlinx.coroutines.withContext

@Composable
fun PassengerMap(navController: NavController, uuid: String, departure: String, name: String){
    var isButtonClicked by remember { mutableStateOf(false) }
    var nameValue by remember { mutableStateOf("") }
    var requestValue by remember { mutableStateOf("") }
    Log.d("PassengerMap", "UUID: $uuid")

    Column {
        TopAppBarSample()
        BottomBar(
            navController,
            isButtonClicked = isButtonClicked,
            setButtonClicked = { isButtonClicked = it }
        ) { innerPadding ->
            if (departure != null) {
                AdjustPassengerRequestScreenUI(
                    departure = departure,
                    isButtonClicked = isButtonClicked,
                    namePassenger = nameValue,
                    request = requestValue,
                    onNameChange = { newName -> nameValue = newName },
                    onRequestChange = { newRequest -> requestValue = newRequest }
                )
            }
        }
    }

    LaunchedEffect(nameValue, requestValue) {
        Log.d("PassengerMap", "nameValue updated: $nameValue")
        Log.d("PassengerMap", "requestValue updated: $requestValue")
    }

    // ボタンがクリックされたかの状態変化を監視
    LaunchedEffect(isButtonClicked) {
        if (isButtonClicked) {
            Log.d("PassengerMap", "Button clicked with: NameValue: $nameValue, RequestValue: $requestValue")
            val dataBase = DataBase()
            val currentNameValue = nameValue
            val currentRequestValue = requestValue
            launch(Dispatchers.IO) {
                try {
                    dataBase.incrementPassenger(uuid, name, currentNameValue, currentRequestValue)
                    withContext(Dispatchers.Main) {
                        isButtonClicked = false
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
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

                        val adjustedLatLng = LatLng(searchedLatLng!!.latitude - 0.005, searchedLatLng!!.longitude)

                        googleMap.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                //searchedLatLng!!,
                                adjustedLatLng,
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


