package com.example.managerbookfreelancer.core.dataBase.dao

import androidx.room.*
import com.example.managerbookfreelancer.core.entity.JobEntity

@Dao
interface JobDAO {

    @Query("SELECT * FROM job WHERE dateOfEvent <= :currentDay ORDER BY dateOfEvent, timeOfEvent DESC")
    suspend fun getOldJobs(currentDay: Long): List<JobEntity>

    @Query("SELECT * FROM job WHERE dateOfEvent >= :currentDay ORDER BY dateOfEvent, timeOfEvent ASC")
    suspend fun getFutureJobs(currentDay: Long): List<JobEntity>

    suspend fun getAll(currentDay: Long, showOlditens: Boolean): List<JobEntity> {
        return if (showOlditens) {
            getOldJobs(currentDay)
        } else {
            getFutureJobs(currentDay)
        }
    }

    @Query("SELECT * FROM job WHERE id_job = :idjob")
    suspend fun getJobById(idjob: Long) : JobEntity


    @Query("SELECT * FROM job WHERE dateOfEvent >= :currentDay ORDER BY dateOfEvent ASC LIMIT 1")
    suspend fun getNextEvent(currentDay: Long) : JobEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(jobEntity: JobEntity)

    @Query("DELETE FROM job WHERE id_job = :id")
    suspend fun delete(id: Long)
}