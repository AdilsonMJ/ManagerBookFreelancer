package com.example.managerbookfreelancer.core.repository

import com.example.managerbookfreelancer.core.entity.JobEntity


interface JobsRepository{

    suspend fun fetchJobs(currentDay: Long, showOlditens: Boolean) : List<JobEntity>

    suspend fun getNextJob(currentDay: Long) : JobEntity

    suspend fun getJobById(idJob: Long) : JobEntity

    suspend fun insert(jobEntity: JobEntity)

    suspend fun delete(id: Long)

}