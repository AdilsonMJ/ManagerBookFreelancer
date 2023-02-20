package com.example.managerbookfreelancer.core.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "job")
data class JobEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "idJob") val idJob : Long = 0,

    @ColumnInfo(name = "engaged")
    val engaged : String,

    @ColumnInfo(name = "ownerName")
    val ownerName : String,

    @ColumnInfo(name = "weedingDay")
    val weedingDay : String ? = null,

    @ColumnInfo(name = "weedingTime")
    val weedingTime: String ? = null,

    @ColumnInfo(name = "weedingCity")
    val weedingCity: String,

    @Embedded val professional: ProfessionalEntity
)


