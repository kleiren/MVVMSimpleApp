package com.carlos.whentorun.presentation.compose.weather

import android.location.Location
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlos.whentorun.common.Resource
import com.carlos.whentorun.domain.manager.CoordinatesManager
import com.carlos.whentorun.domain.manager.CoordinatesManagerListener
import com.carlos.whentorun.domain.usecases.GetWeatherAtLocationUseCase
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherAtLocationUseCase: GetWeatherAtLocationUseCase,
    private val coordinatesManager: CoordinatesManager
) : ViewModel() {

    private val _state = mutableStateOf(WeatherViewState())
    val state: State<WeatherViewState> = _state

    init {
        getCurrentLocation()
    }

    private fun getCurrentLocation() {
        coordinatesManager.setListeners(object : CoordinatesManagerListener {
            override fun onLocationChanged(location: Location) {
                _state.value = state.value.copy(
                    location = LatLng(location.latitude, location.longitude),
                    updateCameraPosition = true
                )
                getWeatherForLocation()
            }
        })
        coordinatesManager.getLastLocation()
    }

    private fun getWeatherForLocation() {
        state.value.location?.let {
            getWeatherAtLocationUseCase(it).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            weather = result.data ?: emptyList(),
                            isLoading = false,
                            showError = false
                        )
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            weather = emptyList(),
                            isLoading = false,
                            showError = true
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            weather = emptyList(),
                            isLoading = true,
                            showError = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun updateLocation(location: LatLng) {
        _state.value = state.value.copy(
            location = location,
            updateCameraPosition = false
        )
        getWeatherForLocation()
    }

    fun dismissErrorDialog() {
        _state.value = state.value.copy(
            showError = false
        )
    }
}