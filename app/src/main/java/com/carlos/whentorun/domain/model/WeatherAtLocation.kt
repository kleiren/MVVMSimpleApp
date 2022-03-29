package com.carlos.whentorun.domain.model

import java.util.*

data class WeatherAtLocation(
    val hourlyWeather: List<HourlyWeather>? = emptyList()
){
    data class HourlyWeather(
        val date: Date?,
        val temperature: Double?
    )
}