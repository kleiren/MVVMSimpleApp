package com.carlos.whentorun.presentation.compose.weather.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.carlos.whentorun.R

@Composable
fun ShowErrorDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = { onDismiss() })
            { Text(text = stringResource(R.string.dialog_error_ok)) }
        },
        title = { Text(text = stringResource(R.string.dialog_error_title)) },
        text = { Text(text = stringResource(R.string.dialog_error_description)) }
    )
}
