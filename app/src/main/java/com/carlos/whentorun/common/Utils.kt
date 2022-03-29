package com.carlos.whentorun.common

import java.text.SimpleDateFormat
import java.util.*

fun Date.toFormattedString(): String = SimpleDateFormat(
    "dd/MM HH:mm",
    Locale.getDefault()
).format(this).toString()