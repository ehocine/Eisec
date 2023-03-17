package com.helic.eisec.utils

import android.annotation.SuppressLint
import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.*


const val DUE_DATE_FORMAT = "EEE, d MMM yyyy HH:mm aaa"
//const val DUE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS"

fun formatCalendar(calendar: Calendar, dateTimeFormat: String? = DUE_DATE_FORMAT): String {
    val simpleDateFormat = SimpleDateFormat(dateTimeFormat, Locale.getDefault())
    return simpleDateFormat.format(calendar.time)
}

fun getCalendar(dateTime: String): Calendar {
    val simpleDateFormat = SimpleDateFormat(DUE_DATE_FORMAT, Locale.getDefault())
    val cal = Calendar.getInstance()
    try {
        simpleDateFormat.parse(dateTime)?.let {
            cal.time = it
        }
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return cal
}

@SuppressLint("SimpleDateFormat")
fun getLocalDate(): String {
    val simpleDateFormat = SimpleDateFormat(DUE_DATE_FORMAT)
    val localDate = LocalDate.now()
    val startOfDay: LocalDateTime = localDate.atTime(LocalTime.now())
    val timestamp = startOfDay.atZone(ZoneId.systemDefault()).toInstant().epochSecond
    val date = Date(timestamp * 1000L)

    Log.d("Tag", date.toString())
    return simpleDateFormat.format(date)
}

@SuppressLint("SimpleDateFormat")
fun convertTimeStampToDate(epoch: Long): String {
    val date = Date(epoch)
    val sdf = SimpleDateFormat("EEE, d MMM yyyy HH:mm aaa")
    return sdf.format(date)
}