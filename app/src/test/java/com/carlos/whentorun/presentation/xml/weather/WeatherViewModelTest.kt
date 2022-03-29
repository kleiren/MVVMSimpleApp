package com.carlos.whentorun.presentation.xml.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.carlos.whentorun.common.Resource
import com.carlos.whentorun.domain.manager.CoordinatesManager
import com.carlos.whentorun.domain.model.WeatherAtLocation.HourlyWeather
import com.carlos.whentorun.domain.usecases.GetWeatherAtLocationUseCase
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class WeatherViewModelTest {

    @Mock
    lateinit var coordinatesManager: CoordinatesManager

    @Mock
    lateinit var getWeatherAtLocationUseCase: GetWeatherAtLocationUseCase

    private lateinit var weatherViewModel: WeatherViewModel

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(TestCoroutineDispatcher())

        weatherViewModel = WeatherViewModel(
            getWeatherAtLocationUseCase = getWeatherAtLocationUseCase,
            coordinatesManager = coordinatesManager
        )
    }

    @Test
    fun `get weather for location - success`() {

        runBlocking {
            Mockito.`when`(getWeatherAtLocationUseCase(LOCATION))
                .thenReturn(flow { emit(Resource.Success(WEATHER)) })

            weatherViewModel.updateLocation(LOCATION)

            assertEquals(WEATHER, weatherViewModel.viewState.value?.weather)
        }
    }

    @Test
    fun `get weather for location - error`() {

        runBlocking {
            Mockito.`when`(getWeatherAtLocationUseCase(LOCATION)).thenReturn(
                flow {
                    emit(
                        Resource.Error(ERROR_MESSAGE)
                    )
                })

            weatherViewModel.updateLocation(LOCATION)

            assertTrue(weatherViewModel.viewState.value?.showError!!)
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
        const val ERROR_MESSAGE = "error"
    }
}