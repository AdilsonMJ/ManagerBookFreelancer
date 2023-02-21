package com.example.managerbookfreelancer.core.repository

import com.example.managerbookfreelancer.core.dataBase.dao.JobDAO
import com.example.managerbookfreelancer.core.model.JobEntity
import kotlinx.coroutines.flow.Flow

class JobsRepositoryImpl(
    private val jobDao: JobDAO
) : JobsRepository {

        override fun fetchJobs(currentDay: Long, showOlditens: Boolean): Flow<List<JobEntity>> = jobDao.getAll(currentDay, showOlditens)

        override suspend fun insert(jobEntity: JobEntity) {
            jobDao.insert(jobEntity)
        }

        override suspend fun delete(jobEntity: JobEntity) {
            jobDao.delete(jobEntity)
        }
    }
