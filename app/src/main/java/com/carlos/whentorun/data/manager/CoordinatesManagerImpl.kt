package com.carlos.whentorun.data.manager

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.carlos.whentorun.domain.manager.CoordinatesManager
import com.carlos.whentorun.domain.manager.CoordinatesManagerListener
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class CoordinatesManagerImpl constructor(
    val context: Context
) : CoordinatesManager {

    private var coordinatesManagerListener: CoordinatesManagerListener? = null

    private var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    override fun setListeners(coordinatesManagerListener: CoordinatesManagerListener) {
        this.coordinatesManagerListener = coordinatesManagerListener
    }

    override fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    coordinatesManagerListener?.onLocationChanged(location)
                }
            }
    }

}
