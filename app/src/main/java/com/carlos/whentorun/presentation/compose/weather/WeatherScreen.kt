package com.carlos.whentorun.presentation.compose.weather

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.carlos.whentorun.presentation.compose.weather.components.MapViewContainer
import com.carlos.whentorun.presentation.compose.weather.components.ShowErrorDialog
import com.carlos.whentorun.presentation.compose.weather.components.WeatherListItem
import com.google.accompanist.permissions.ExperimentalPermissionsApi


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Column {
        Box(modifier = Modifier.height(350.dp)) {
            state.location?.let { currentLocation ->
                MapViewContainer(
                    location = currentLocation,
                    updateCameraPosition = state.updateCameraPosition,
                    onMapClick = { newLocation ->
                        viewModel.updateLocation(newLocation)
                    }
                )
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                state.showError -> {
                    ShowErrorDialog(onDismiss = { viewModel.dismissErrorDialog() })
                }
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(state.weather) { weather ->
                            WeatherListItem(
                                weather = weather
                            )
                        }
                    }
                }
            }
        }
    }
}

