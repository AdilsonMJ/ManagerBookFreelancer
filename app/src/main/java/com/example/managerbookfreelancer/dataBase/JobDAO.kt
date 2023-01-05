package com.example.managerbookfreelancer.dataBase

import androidx.room.*
import com.example.managerbookfreelancer.model.JobEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDAO {

    @Query("SELECT * FROM job ORDER BY weedingDay")
    fun getAll(): Flow<List<JobEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(jobEntity: JobEntity)

    @Delete
    suspend fun delete(jobEntity: JobEntity)
}