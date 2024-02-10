package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker() {
    val dateTime = LocalDateTime.now()

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = dateTime.toMillis(),
        yearRange = (2023..2024),
        initialDisplayMode = DisplayMode.Picker
    )

    DatePicker(state = datePickerState)
}
