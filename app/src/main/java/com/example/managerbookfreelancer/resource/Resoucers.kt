package com.example.managerbookfreelancer.resource

import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat

object Resoucers {

    fun fromLongToString(date: Long): String{
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        return simpleDateFormat.format(date)
    }


    @RequiresApi(Build.VERSION_CODES.N)
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