package com.example.managerbookfreelancer.main.core.dataBase.dao

import androidx.room.*
import com.example.managerbookfreelancer.main.core.entity.ClientEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ClientDAO {

    @Query("Select * FROM client ORDER BY name ASC")
    fun getAll(): Flow<List<ClientEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(clientEntity: ClientEntity)

    @Query("SELECT * FROM client WHERE id_Client = :id")
    suspend fun getClientByID(id: Long) : ClientEntity

    @Query("DELETE FROM client WHERE id_Client = :id")
    suspend fun delete(id: Long)

}