package com.carlos.whentorun.domain.usecases

import com.carlos.whentorun.common.Resource
import com.carlos.whentorun.domain.model.WeatherAtLocation.HourlyWeather
import com.carlos.whentorun.domain.model.WeatherAtLocation
import com.carlos.whentorun.domain.repository.WeatherRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.io.IOException
import java.util.*

class GetWeatherAtLocationUseCaseTest {

    private lateinit var getWeatherAtLocationUseCase: GetWeatherAtLocationUseCase

    @Mock
    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getWeatherAtLocationUseCase = GetWeatherAtLocationUseCase(
            weatherRepository = weatherRepository
        )
    }

    @Test
    fun `get weather at location - success`() {

        runBlocking {

            `when`(weatherRepository.getWeatherAtLocation(anyString(), anyString())).thenReturn(
                WeatherAtLocation(WEATHER)
            )
            val result = getWeatherAtLocationUseCase(LOCATION).toList()

            assert(result.first() is Resource.Loading)
            assert(result[1] is Resource.Success)
            assertEquals(result[1].data, WEATHER)
        }
    }

    @Test
    fun `get weather at location - error`() {

        runBlocking {

            `when`(weatherRepository.getWeatherAtLocation(anyString(), anyString())).then {
                throw IOException()
            }

            val result = getWeatherAtLocationUseCase(LOCATION).toList()

            assert(result.first() is Resource.Loading)
            assert(result[1] is Resource.Error)

        }
    }

    companion object {
        val LOCATION = LatLng(40.1, -3.7)
        val WEATHER = listOf(
            HourlyWeather(
                date = Date(1647025715945),
                temperature = 10.0
            )
        )
    }


}