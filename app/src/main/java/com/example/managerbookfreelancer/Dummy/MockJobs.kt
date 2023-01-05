package com.example.managerbookfreelancer.Dummy

import com.example.managerbookfreelancer.core.JobsRepository

//object MockJobs : JobsRepository {
//
//    private val jobsListMock: MutableList<JobModel> = mutableListOf()
//
//    override fun fetchHabits() = jobsListMock.map { it.copy() }
//
//    override fun addNewJob(jobModel: JobModel) {
//        jobsListMock.add(
//            JobModel(
//                engaged = jobModel.engaged,
//                ownerName = jobModel.ownerName,
//                weedingDay = jobModel.weedingDay,
//                weedingTime = jobModel.weedingTime,
//                weedingCity = jobModel.weedingCity
//            )
//        )
//    }
//
//    override fun removeJob(jobModel: JobModel) {
//
//        jobsListMock.remove(jobModel)
//
//    }
//}