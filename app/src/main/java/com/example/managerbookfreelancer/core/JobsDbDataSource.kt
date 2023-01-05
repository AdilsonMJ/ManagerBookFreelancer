package com.example.managerbookfreelancer.core

import com.example.managerbookfreelancer.dataBase.JobDAO
import com.example.managerbookfreelancer.model.JobEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class JobsDbDataSource(
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
