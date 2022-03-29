package com.carlos.whentorun.presentation.xml.weather

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _viewState = MutableLiveData<WeatherViewState>()
    val viewState: LiveData<WeatherViewState> = _viewState

    init {
        _viewState.value = WeatherViewState()
        getCurrentLocation()
    }

    private fun getCurrentLocation() {
        coordinatesManager.setListeners(object : CoordinatesManagerListener {
            override fun onLocationChanged(location: Location) {
                _viewState.value = viewState.value?.copy(
                    location = LatLng(location.latitude, location.longitude),
                    updateCameraPosition = true
                )
                getWeatherForLocation()
            }
        })
        coordinatesManager.getLastLocation()
    }

    private fun getWeatherForLocation() {
        viewState.value?.location?.let {
            getWeatherAtLocationUseCase(it).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _viewState.value = viewState.value?.copy(
                            weather = result.data ?: emptyList(),
                            showError = false,
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _viewState.value = viewState.value?.copy(
                            showError = true,
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        _viewState.value = viewState.value?.copy(
                            weather = emptyList(),
                            showError = false,
                            isLoading = true
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun updateLocation(location: LatLng) {
        _viewState.value = viewState.value?.copy(
            weather = emptyList(),
            location = LatLng(location.latitude, location.longitude),
            updateCameraPosition = false
        )
        getWeatherForLocation()
    }

}