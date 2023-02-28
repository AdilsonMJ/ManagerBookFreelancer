package com.example.managerbookfreelancer.core.dataBase.dao

import androidx.room.*
import com.example.managerbookfreelancer.core.entity.ClientEntity


@Dao
interface ClientDAO {

    @Query("Select * FROM client ORDER BY name ASC")
    suspend fun getAll(): List<ClientEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(professionalDAO: ClientEntity)

    @Delete
    suspend fun delete(professionalDAO: ClientEntity)

}