package com.example.managerbookfreelancer.adapter.core

import com.example.managerbookfreelancer.model.JobModel

interface JobsRepository {

    fun fetchListJobs() : List<JobModel>

}