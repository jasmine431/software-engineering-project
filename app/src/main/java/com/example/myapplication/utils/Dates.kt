package com.example.myapplication.utils


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Long.epochMillisToLocalDate(): LocalDate = Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()

fun LocalDate.toEpochMillis() = this.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

fun LocalDate.toYyMmDdString(): String {
    val formatter = DateTimeFormatter.ofPattern("yy-MM-dd")
    return this.format(formatter)
}

@OptIn(ExperimentalMaterial3Api::class)
fun TimePickerState.toLocalTime(): LocalTime = LocalTime.of(this.hour, this.minute)
