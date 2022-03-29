package com.carlos.whentorun.presentation.compose.weather

import com.carlos.whentorun.domain.model.WeatherAtLocation.HourlyWeather
import com.google.android.gms.maps.model.LatLng

data class WeatherViewState(
    val weather: List<HourlyWeather> = emptyList(),
    val location: LatLng? = null,
    val updateCameraPosition: Boolean = false,
    val isLoading: Boolean = false,
    val showError: Boolean = false
)
