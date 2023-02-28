package com.example.managerbookfreelancer.core.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "client")
data class ClientEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id_Client") val idClient: Long = 0,
    val name:String,
    val contact : String,
    val email : String,

    ){
    override fun toString(): String {
        return name
    }
}


