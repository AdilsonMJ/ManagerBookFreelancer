package com.example.managerbookfreelancer.core.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "job")
data class JobEntity(

    @PrimaryKey @ColumnInfo(name = "idJob") val idJob : String,
    val engaged : String,
    val ownerName : String,
    val weedingDay : Long,
    val weedingTime: String ? = null,
    val weedingCity: String,
    val professionalId: Long
)


