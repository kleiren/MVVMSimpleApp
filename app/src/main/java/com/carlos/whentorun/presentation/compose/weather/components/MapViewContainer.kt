package com.carlos.whentorun.presentation.compose.weather.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun MapViewContainer(
    location: LatLng,
    updateCameraPosition: Boolean,
    onMapClick: (LatLng) -> Unit
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 10f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapClick = onMapClick
    ) {
        Marker(position = location)
        if (updateCameraPosition)
            cameraPositionState.position = CameraPosition.fromLatLngZoom(location, 10f)
    }
}

