package com.example.managerbookfreelancer.core

import com.example.managerbookfreelancer.core.model.JobEntity
import kotlinx.coroutines.flow.Flow


interface JobsRepository{

     fun fetchHabits() : Flow<List<JobEntity>>

    suspend fun insert(jobEntity: JobEntity)

    suspend fun delete(jobEntity: JobEntity)

}