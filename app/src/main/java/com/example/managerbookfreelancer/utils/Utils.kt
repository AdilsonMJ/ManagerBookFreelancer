package com.example.managerbookfreelancer.utils

import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun formatDate(date: Long): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(Date(date))
    }

    fun getDateInMillesWithoutTime(): Long{
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }


     fun getFormattedTime(hours: Int, minutes: Int): String {
        val hourString = if (hours < 10) "0$hours" else hours.toString()
        val minuteString = if (minutes < 10) "0$minutes" else "$minutes"
        val timeFormatted = "$hourString:$minuteString"
        return if (hours < 12) "$timeFormatted AM" else "$timeFormatted PM"
    }

}