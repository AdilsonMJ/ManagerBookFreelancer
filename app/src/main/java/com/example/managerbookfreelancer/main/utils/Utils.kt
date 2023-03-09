package com.example.managerbookfreelancer.main.utils

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

}