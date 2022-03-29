package com.carlos.whentorun.presentation.compose.weather.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.carlos.whentorun.R
import com.carlos.whentorun.common.toFormattedString
import com.carlos.whentorun.domain.model.WeatherAtLocation.HourlyWeather

@Composable
fun WeatherListItem(
    weather: HourlyWeather
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(
                id = R.string.date_template,
                weather.date?.toFormattedString() ?: ""
            ),
            style = MaterialTheme.typography.body1,
        )
        Text(
            text = stringResource(
                id = R.string.temperature_template,
                weather.temperature.toString()
            ),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.body1,
        )
    }
}


