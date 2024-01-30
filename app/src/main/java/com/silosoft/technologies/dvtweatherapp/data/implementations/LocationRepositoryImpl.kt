package com.silosoft.technologies.dvtweatherapp.data.implementations

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.silosoft.technologies.dvtweatherapp.domain.repository.LocationRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

// Inspired from here:
// https://medium.com/@daniel.atitienei/get-current-user-location-in-jetpack-compose-using-clean-architecture-android-6683abca66c9
class LocationRepositoryImpl @Inject constructor(
    private val application: Application,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : LocationRepository {

    override suspend fun getCurrentLocation(): Pair<Double, Double>? {
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager = application.getSystemService(
            Context.LOCATION_SERVICE
        ) as LocationManager

        val isGpsEnabled = locationManager
            .isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!isGpsEnabled && !(hasAccessCoarseLocationPermission || hasAccessFineLocationPermission)) {
            return null
        }


        return suspendCancellableCoroutine { continuation ->
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    continuation.resume(location?.let { Pair(it.latitude, it.longitude) })
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
                .addOnCanceledListener {
                    continuation.cancel()
                }
        }
    }
}
