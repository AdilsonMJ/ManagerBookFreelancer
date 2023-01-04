package com.example.managerbookfreelancer.DATABASE

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.managerbookfreelancer.model.JobModelEntity

@Dao
interface JobDAO {

    @Query("SELECT * FROM job ORDER BY weedingDay ASC")
    fun getAll(): LiveData<List<JobModelEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(jobModelEntity: JobModelEntity)

    @Delete
    suspend fun delete(jobModelEntity: JobModelEntity)
}