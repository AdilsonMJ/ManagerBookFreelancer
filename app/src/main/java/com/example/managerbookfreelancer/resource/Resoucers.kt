package com.example.managerbookfreelancer.resource

import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.util.*

object Resoucers {

    fun fromLongToString(date: Long): String{
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return simpleDateFormat.format(date)
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