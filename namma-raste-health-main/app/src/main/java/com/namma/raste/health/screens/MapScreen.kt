package com.namma.raste.health.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@SuppressLint("MissingPermission")
@Composable
fun MapScreen() {

    val context = LocalContext.current

    var userLocation by remember {
        mutableStateOf<LatLng?>(null)
    }

    var permissionGranted by remember {
        mutableStateOf(false)
    }

    val fusedLocationClient =
        remember {
            LocationServices.getFusedLocationProviderClient(context)
        }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { granted ->

            permissionGranted = granted

            if (granted) {

                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location ->

                        location?.let {

                            userLocation = LatLng(
                                it.latitude,
                                it.longitude
                            )
                        }
                    }
            }
        }

    LaunchedEffect(Unit) {

        when {

            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {

                permissionGranted = true

                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location ->

                        location?.let {

                            userLocation = LatLng(
                                it.latitude,
                                it.longitude
                            )
                        }
                    }
            }

            else -> {
                permissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        userLocation?.let { location ->

            val cameraPositionState =
                rememberCameraPositionState {

                    position = CameraPosition.fromLatLngZoom(
                        location,
                        15f
                    )
                }

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {

                Marker(
                    state = MarkerState(position = location),
                    title = "You are here"
                )
            }

        } ?: run {

            CircularProgressIndicator()
        }
    }
}