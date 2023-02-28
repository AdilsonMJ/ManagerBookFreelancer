package com.example.managerbookfreelancer.core.dataBase.dao

import androidx.room.*
import com.example.managerbookfreelancer.core.entity.JobEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDAO {

    @Query("SELECT * FROM job WHERE dateOfEvent <= :currentDay ORDER BY dateOfEvent, timeOfEvent DESC")
     fun getOldJobs(currentDay: Long): Flow<List<JobEntity>>

    @Query("SELECT * FROM job WHERE dateOfEvent >= :currentDay ORDER BY dateOfEvent, timeOfEvent ASC")
     fun getFutureJobs(currentDay: Long): Flow<List<JobEntity>>

     fun getAll(currentDay: Long, showOlditens: Boolean): Flow<List<JobEntity>> {
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