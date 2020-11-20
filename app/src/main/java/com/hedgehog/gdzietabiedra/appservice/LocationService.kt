package com.hedgehog.gdzietabiedra.appservice

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import com.github.asvid.biedra.domain.Location
import com.github.asvid.biedra.domain.location
import com.google.android.gms.location.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private val WARSAW = location {
    lat = 52.229990
    lng = 21.011572
}

@SuppressLint("MissingPermission")
class LocationService(private val context: Context) {

    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    suspend fun getLocation(): LocationServiceResult = suspendCoroutine {
        if (!isPermissionGranted()) {
            it.resume(PermissionRequired)
        }
        if (isLocationServiceAvailable()) {
            fusedLocationClient.lastLocation.addOnSuccessListener { foundLocation ->
                if (foundLocation == null) {
                    fusedLocationClient.requestLocationUpdates(getLocationRequest(), object : LocationCallback() {
                        override fun onLocationResult(locationServiceResult: LocationResult?) {
                            if (locationServiceResult != null && locationServiceResult.locations.isNotEmpty()) {
                                val newLocation = locationServiceResult.locations[0]
                                it.resume(Success(Location(newLocation.latitude, newLocation.longitude)))
                            } else {
                                it.resume(Error("location was not available"))
                            }
                        }
                    }, Looper.myLooper())
                } else {
                    it.resume(Success(Location(foundLocation.latitude, foundLocation.longitude)))
                }
            }
        } else {
            it.resume(LocationNotAvailable)
        }
    }

    private fun getLocationRequest() = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        setExpirationDuration(5000)
        numUpdates = 1
    }

    private fun isLocationServiceAvailable(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        return isGpsEnabled or isNetworkEnabled
    }

    private fun isPermissionGranted(): Boolean {
        return when (PackageManager.PERMISSION_GRANTED) {
            context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) -> true
            else -> false
        }
    }
}

sealed class LocationServiceResult
data class Success(val location: Location) : LocationServiceResult()
data class Error(val message: String) : LocationServiceResult()
object PermissionRequired : LocationServiceResult()
object LocationNotAvailable : LocationServiceResult()