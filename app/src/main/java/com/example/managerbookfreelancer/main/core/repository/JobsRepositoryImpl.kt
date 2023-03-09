package com.example.managerbookfreelancer.main.core.repository

import com.example.managerbookfreelancer.main.core.dataBase.dao.JobDAO
import com.example.managerbookfreelancer.main.core.entity.JobEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class JobsRepositoryImpl @Inject constructor(
    private val jobDao: JobDAO
) : JobsRepository {

    override  fun fetchJobs(currentDay: Long, showOlditens: Boolean): Flow<List<JobEntity>> =
        jobDao.getAll(currentDay, showOlditens)

    override suspend fun getNextJob(currentDay: Long): JobEntity = jobDao.getNextEvent(currentDay = currentDay)

    override suspend fun getJobById(idJob: Long): JobEntity = jobDao.getJobById(idjob = idJob)

    override suspend fun insert(jobEntity: JobEntity) {
        jobDao.insert(jobEntity)
    }

    override suspend fun delete(id: Long) {
        jobDao.delete(id)
    }
}
