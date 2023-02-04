package com.example.managerbookfreelancer.core.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "job")
data class JobEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id : String,

    @ColumnInfo(name = "engaged")
    val engaged : String,

    @ColumnInfo(name = "ownerName")
    val ownerName : String,

    @ColumnInfo(name = "weedingDay")
    val weedingDay : String ? = null,

    @ColumnInfo(name = "weedingTime")
    val weedingTime: String ? = null,

    @ColumnInfo(name = "weedingCity")
    val weedingCity: String
)


