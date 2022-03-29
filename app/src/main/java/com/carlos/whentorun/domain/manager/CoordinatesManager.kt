package com.carlos.whentorun.domain.manager

import android.location.Location


interface CoordinatesManager {
    fun setListeners(coordinatesManagerListener: CoordinatesManagerListener)
    fun getLastLocation()
}

interface CoordinatesManagerListener {
    fun onLocationChanged(location: Location)
}
