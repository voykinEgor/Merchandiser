package com.example.merchandiser.presentation.map

import android.annotation.SuppressLint
import android.app.Application
import android.app.PendingIntent
import android.content.Context.LOCATION_SERVICE
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.merchandiser.LOG
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.yandex.mapkit.geometry.Point
import javax.inject.Inject

class MapViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {
    private val _locationSettingsCheck = MutableLiveData<PendingIntent?>(null)
    val locationSettingsCheck = _locationSettingsCheck

    private val _locationState = MutableLiveData<LocationState>()
    val locationState: LiveData<LocationState> = _locationState

    sealed class LocationState {
        object Enabled : LocationState()
        object Disabled : LocationState()
        object ResolutionRequired : LocationState()
        data class Coordinates(val point: Point) : LocationState()
    }


    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)


    internal val locationRequest = LocationRequest.create().apply {
        interval = 10000
        fastestInterval = 5000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            locationResult.lastLocation?.let { location ->
                _locationState.value =
                    LocationState.Coordinates(Point(location.latitude, location.longitude))
                // Останавливаем обновления после получения первой локации
                fusedLocationClient.removeLocationUpdates(this)
            }
        }
    }


    fun checkLocationSettings() {
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client = LocationServices.getSettingsClient(getApplication<Application>())
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            _locationState.value = LocationState.Enabled
            checkAndRequestLocation()
            Log.e(LOG, "SUCCESS")
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                _locationState.value = LocationState.ResolutionRequired
                _locationSettingsCheck.value = exception.resolution
            } else {
                Log.e(LOG, "Ошибка обновления геолокации: ${exception.message}")
            }
        }
    }

    fun onLocationSettingsSuccess() {
        _locationState.value = LocationState.Enabled
        checkAndRequestLocation()
    }

    fun onLocationSettingsDenied() {
        _locationState.value = LocationState.Disabled
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getApplication<Application>()
            .getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    @SuppressLint("MissingPermission")
    fun checkAndRequestLocation() {
        if (!isLocationEnabled()) {
            _locationState.value = LocationState.Disabled
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { lastLocation ->
            if (lastLocation != null) {
                _locationState.value =
                    LocationState.Coordinates(Point(lastLocation.latitude, lastLocation.longitude))
            } else {
                requestLocationUpdates()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}