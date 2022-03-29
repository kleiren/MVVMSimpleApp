package com.carlos.whentorun.data.net

import android.net.Uri
import com.carlos.whentorun.data.enviroment.Environment.WEATHER_API_KEY
import com.carlos.whentorun.data.model.WeatherResponse
import com.carlos.whentorun.domain.exception.WeatherApiException
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class WeatherApi {

    suspend fun getWeatherAtLocation(latitude: String, longitude: String): WeatherResponse =
        withContext(Dispatchers.IO) {
            val url = Uri.parse(BASE_URL)
                .buildUpon()
                .appendQueryParameter("lat", latitude)
                .appendQueryParameter("lon", longitude)
                .appendQueryParameter("appid", WEATHER_API_KEY)
                .appendQueryParameter("exclude", "minutely,daily,current")
                .appendQueryParameter("units", "metric")
                .build().toString()

            val response = apiCall(url, "GET")
            return@withContext Gson().fromJson(response, WeatherResponse::class.java)
        }

    private fun apiCall(url: String, requestMethod: String): String {
        val response: String
        val httpURLConnection = URL(url).openConnection() as HttpURLConnection
        httpURLConnection.requestMethod = requestMethod
        httpURLConnection.setRequestProperty("Content-Type", "application/json")
        try {
            httpURLConnection.connect()
            val br: BufferedReader = if (httpURLConnection.responseCode in CORRECT_RESPONSE_RANGE) {
                BufferedReader(InputStreamReader(httpURLConnection.inputStream))
            } else {
                throw WeatherApiException(BufferedReader(InputStreamReader(httpURLConnection.errorStream)).readText())
            }
            response = br.readText()
        } finally {
            httpURLConnection.disconnect()
        }
        if (response.isEmpty()) throw WeatherApiException()
        return response
    }

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/onecall"
        val CORRECT_RESPONSE_RANGE = 100..399
    }
}



