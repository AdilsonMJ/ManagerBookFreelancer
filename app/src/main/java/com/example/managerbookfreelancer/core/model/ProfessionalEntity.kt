package com.example.managerbookfreelancer.core.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "professional")
data class ProfessionalEntity(
    @PrimaryKey @ColumnInfo(name = "idProfessional") val idProfessional: String,

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


