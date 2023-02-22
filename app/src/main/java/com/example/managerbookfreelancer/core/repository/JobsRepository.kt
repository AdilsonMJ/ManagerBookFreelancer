package com.example.managerbookfreelancer.core.repository

import com.example.managerbookfreelancer.core.entity.JobEntity
import kotlinx.coroutines.flow.Flow


interface JobsRepository{

    fun fetchJobs(currentDay: Long, showOlditens: Boolean) : Flow<List<JobEntity>>

    suspend fun getNextJob(currentDay: Long) : JobEntity

    suspend fun insert(jobEntity: JobEntity)

    suspend fun delete(jobEntity: JobEntity)

}