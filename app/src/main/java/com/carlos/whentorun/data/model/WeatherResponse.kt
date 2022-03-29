package com.carlos.whentorun.data.model

import com.carlos.whentorun.domain.model.WeatherAtLocation
import com.google.gson.annotations.SerializedName
import java.util.*

data class WeatherResponse(
    @SerializedName("hourly")
    val hourly: List<Hourly>?
) {
    data class Hourly(
        @SerializedName("dt")
        val dt: Int?,
        @SerializedName("temp")
        val temp: Double?,
    ) {
        fun toDomainModel() = WeatherAtLocation.HourlyWeather(
            date = dt?.toLong()?.times(SEC_TO_MILLIS_MULTIPLIER)?.let { Date(it) },
            temperature = temp
        )

        companion object {
            const val SEC_TO_MILLIS_MULTIPLIER = 1000
        }
    }

    fun toDomainModel() = WeatherAtLocation(
        hourlyWeather = hourly?.map { it.toDomainModel() }
    )
}