package com.namma.raste.health.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await
import java.util.Locale

object LocationUtils {
    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(context: Context): Pair<Double, Double>? {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        return try {
            val location = fusedLocationClient.lastLocation.await()
            location?.let { it.latitude to it.longitude }
        } catch (e: Exception) {
            null
        }
    }

    fun getAddress(context: Context, lat: Double, lng: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        return try {
            val addresses = geocoder.getFromLocation(lat, lng, 1)
            if (addresses?.isNotEmpty() == true) {
                addresses[0].getAddressLine(0)
            } else {
                "Unknown Address"
            }
        } catch (e: Exception) {
            "Error fetching address"
        }
    }
}
