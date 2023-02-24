package com.example.managerbookfreelancer.core.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "professional")
data class ProfessionalEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "idProfessional") val idProfessional: Long = 0,
    val name:String,
    val contact : String,
    val email : String,

    ){
    override fun toString(): String {
        return name
    }
}


