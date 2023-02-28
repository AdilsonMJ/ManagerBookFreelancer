package com.example.managerbookfreelancer.core.repository

import com.example.managerbookfreelancer.core.dataBase.dao.JobDAO
import com.example.managerbookfreelancer.core.entity.JobEntity

class JobsRepositoryImpl(
    private val jobDao: JobDAO
) : JobsRepository {

    override suspend fun fetchJobs(currentDay: Long, showOlditens: Boolean): List<JobEntity> = jobDao.getAll(currentDay, showOlditens)

    override suspend fun getNextJob(currentDay: Long): JobEntity = jobDao.getNextEvent(currentDay = currentDay)

    override suspend fun getJobById(idJob: Long): JobEntity = jobDao.getJobById(idjob = idJob)

    override suspend fun insert(jobEntity: JobEntity) {
        jobDao.insert(jobEntity)
    }

    override suspend fun delete(id: Long) {
        jobDao.delete(id)
    }
}
