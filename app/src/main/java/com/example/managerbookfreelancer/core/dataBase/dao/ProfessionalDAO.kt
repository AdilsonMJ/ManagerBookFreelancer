package com.example.managerbookfreelancer.core.dataBase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.managerbookfreelancer.core.model.JobEntity
import com.example.managerbookfreelancer.core.model.ProfessionalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfessionalDAO {

    @Query("Select * FROM professional ORDER BY name DESC")
    fun getAll(): Flow<List<ProfessionalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(professionalDAO: ProfessionalEntity)

    @Delete
    suspend fun delete(professionalDAO: ProfessionalEntity)

}