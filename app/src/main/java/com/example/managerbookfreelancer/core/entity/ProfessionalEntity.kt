package com.example.managerbookfreelancer.core.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "professional")
data class ProfessionalEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "idProfessional") val idProfessional: Long = 0,

    @ColumnInfo(name = "name")
    val name:String,

    @ColumnInfo(name = "contact")
    val contact : String,

    @ColumnInfo(name = "email")
    val email : String,

    ){
    override fun toString(): String {
        return name
    }
}


