package com.carlos.whentorun.domain.usecases

import com.carlos.whentorun.common.Resource
import com.carlos.whentorun.domain.exception.WeatherApiException
import com.carlos.whentorun.domain.model.WeatherAtLocation.HourlyWeather
import com.carlos.whentorun.domain.repository.WeatherRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetWeatherAtLocationUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(location: LatLng): Flow<Resource<List<HourlyWeather>>> =
        flow {
            try {
                emit(Resource.Loading())
                val hourlyWeather =
                    weatherRepository.getWeatherAtLocation(
                        location.latitude.toString(),
                        location.longitude.toString()
                    ).hourlyWeather?.sortedBy {
                        it.temperature
                    } ?: emptyList()
                emit(Resource.Success(hourlyWeather))
            } catch (e: WeatherApiException) {
                emit(Resource.Error(e.message))
            } catch (e: IOException) {
                emit(Resource.Error())
            }
        }
}
