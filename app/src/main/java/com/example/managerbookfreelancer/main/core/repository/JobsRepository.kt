package com.example.managerbookfreelancer.main.core.repository

import com.example.managerbookfreelancer.main.core.entity.JobEntity
import kotlinx.coroutines.flow.Flow


interface JobsRepository{

     fun fetchJobs(currentDay: Long, showOlditens: Boolean) : Flow<List<JobEntity>>

    suspend fun getNextJob(currentDay: Long) : JobEntity

    suspend fun getJobById(idJob: Long) : JobEntity

    suspend fun insert(jobEntity: JobEntity)

    suspend fun delete(id: Long)

}