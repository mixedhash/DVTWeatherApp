package com.silosoft.technologies.dvtweatherapp.extensions

import java.text.SimpleDateFormat
import java.util.Locale

fun String.convertToDayOfWeek(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val date = formatter.parse(this)
    val dayOfWeekFormatter = SimpleDateFormat("EEEE", Locale.getDefault())
    return date?.let { dayOfWeekFormatter.format(it) } ?: ""
}
