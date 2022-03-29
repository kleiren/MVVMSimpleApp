package com.carlos.whentorun.presentation.compose

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.carlos.whentorun.R
import com.carlos.whentorun.presentation.compose.theme.WhenToRunTheme
import com.carlos.whentorun.presentation.compose.weather.WeatherScreen
import com.carlos.whentorun.presentation.xml.MainActivityXml
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalPermissionsApi::class)
class MainActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhenToRunTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val locationPermissionState =
                        rememberPermissionState(Manifest.permission.ACCESS_COARSE_LOCATION)
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text(text = getString(R.string.toolbar_compose_title)) },
                                actions = {
                                    TextButton(onClick = {
                                        startActivity(
                                            Intent(
                                                this@MainActivityCompose,
                                                MainActivityXml::class.java
                                            )
                                        )
                                        this@MainActivityCompose.finish()
                                    }) {
                                        Text(
                                            text = getString(R.string.toolbar_compose_to_xml),
                                            color = Color.White
                                        )
                                    }
                                }
                            )
                        },
                        snackbarHost = {
                            if (locationPermissionState.status is PermissionStatus.Denied) {
                                Snackbar(
                                    action = {
                                        Button(onClick =
                                        { locationPermissionState.launchPermissionRequest() }) {
                                            Text(getString(R.string.location_permission_button))
                                        }
                                    }
                                ) {
                                    Text(getString(R.string.location_permission_description))
                                }
                            }
                        },
                        content = {
                            if (locationPermissionState.status is PermissionStatus.Granted)
                                WeatherScreen()
                        }
                    )
                }
            }
        }
    }
}

