package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import java.time.LocalDateTime
import java.time.ZoneId


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePicker() {
    val dateTime = LocalDateTime.now()

    val dateRangePickerState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = dateTime.toMillis(),
        initialSelectedEndDateMillis = dateTime.plusDays(3).toMillis(),
        yearRange = (2023..2024),
        initialDisplayMode = DisplayMode.Picker
    )

    DateRangePicker(state = dateRangePickerState)
}

fun LocalDateTime.toMillis() =
    atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
