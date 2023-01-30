package com.example.managerbookfreelancer.core

import com.example.managerbookfreelancer.core.dataBase.JobDAO
import com.example.managerbookfreelancer.core.model.JobEntity
import kotlinx.coroutines.flow.Flow

class JobsRepositoryImpl(
    private val jobDao: JobDAO
) : JobsRepository {

        override fun fetchHabits(): Flow<List<JobEntity>> = jobDao.getAll()

        override suspend fun insert(jobEntity: JobEntity) {
            jobDao.insert(jobEntity)
        }

        override suspend fun delete(jobEntity: JobEntity) {
            jobDao.delete(jobEntity)
        }
    }
