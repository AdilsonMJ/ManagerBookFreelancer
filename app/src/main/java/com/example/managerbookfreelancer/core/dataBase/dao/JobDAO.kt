package com.example.managerbookfreelancer.core.dataBase.dao

import androidx.room.*
import com.example.managerbookfreelancer.core.model.JobEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDAO {

    @Query("SELECT * FROM job WHERE weedingDay <= :currentDay ORDER BY weedingDay DESC")
    fun getOldJobs(currentDay: Long): Flow<List<JobEntity>>

    @Query("SELECT * FROM job WHERE weedingDay >= :currentDay ORDER BY weedingDay ASC")
    fun getFutureJobs(currentDay: Long): Flow<List<JobEntity>>

    fun getAll(currentDay: Long, showOlditens: Boolean): Flow<List<JobEntity>> {
        return if (showOlditens) {
            getOldJobs(currentDay)
        } else {
            getFutureJobs(currentDay)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(jobEntity: JobEntity)

    @Delete
    suspend fun delete(jobEntity: JobEntity)
}