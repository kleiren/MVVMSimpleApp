package com.carlos.whentorun.domain.repository

import com.carlos.whentorun.domain.model.WeatherAtLocation

interface WeatherRepository {

    suspend fun getWeatherAtLocation(latitude: String, longitude: String): WeatherAtLocation

}