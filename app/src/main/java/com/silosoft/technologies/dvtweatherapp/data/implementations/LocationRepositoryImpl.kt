package com.silosoft.technologies.dvtweatherapp.data.implementations

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.silosoft.technologies.dvtweatherapp.domain.repository.LocationRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import javax.inject.Inject

// Inspired from here:
// https://medium.com/@daniel.atitienei/get-current-user-location-in-jetpack-compose-using-clean-architecture-android-6683abca66c9
class LocationRepositoryImpl @Inject constructor(
    private val application: Application,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : LocationRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
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

        return suspendCancellableCoroutine { cont ->
            fusedLocationProviderClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) {
                        cont.resume(Pair(result.latitude, result.longitude)) {} // Resume coroutine with location result
                    } else {
                        Timber.e(exception)
                        cont.resume(null) {} // Resume coroutine with null location result
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    cont.resume(Pair(result.latitude, result.longitude)) {}  // Resume coroutine with location result
                }
                addOnFailureListener {
                    Timber.e(it)
                    cont.resume(null) {} // Resume coroutine with null location result
                }
                addOnCanceledListener {
                    cont.cancel() // Cancel the coroutine
                }
            }
        }
    }
}
