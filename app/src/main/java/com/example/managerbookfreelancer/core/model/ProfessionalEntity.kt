package com.example.managerbookfreelancer.core.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "professional")
data class ProfessionalEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,

    @ColumnInfo(name = "name")
    val name:String,

    @ColumnInfo(name = "contact")
    val contact : String,

    @ColumnInfo(name = "email")
    val email : String,

)
