package com.carlos.whentorun.data.repository

import com.carlos.whentorun.data.net.WeatherApi
import com.carlos.whentorun.domain.model.WeatherAtLocation
import com.carlos.whentorun.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : WeatherRepository {
    override suspend fun getWeatherAtLocation(
        latitude: String,
        longitude: String
    ): WeatherAtLocation {
        return weatherApi.getWeatherAtLocation(latitude, longitude).toDomainModel()
    }

}