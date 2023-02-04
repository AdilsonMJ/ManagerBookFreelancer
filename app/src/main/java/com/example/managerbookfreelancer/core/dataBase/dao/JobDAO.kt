package com.example.managerbookfreelancer.core.dataBase.dao

import androidx.room.*
import com.example.managerbookfreelancer.core.model.JobEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDAO {

    @Query("SELECT * FROM job ORDER BY weedingDay DESC, weedingTime DESC")
    fun getAll(): Flow<List<JobEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(jobEntity: JobEntity)

    @Delete
    suspend fun delete(jobEntity: JobEntity)
}