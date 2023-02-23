package com.example.managerbookfreelancer.core.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "job")
data class JobEntity(

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "idJob") val idJob : Long = 0,
    val engaged : String,
    val ownerName : String,
    val weddingDay : Long,
    val weddingTime: String ? = null,
    val weddingCity: String,
    val professionalId: Long
)


