package com.example.managerbookfreelancer.main.core.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "job")
data class JobEntity(

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id_job") val idJob : Long = 0,
    val idClient: Long,

    val customerEndUser : String,
    val dateOfEvent : Long,
    val timeOfEvent: String ? = null,
    val locationOfEvent: String

)


