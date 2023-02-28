package com.example.managerbookfreelancer.core.useCase

import com.example.managerbookfreelancer.core.model.JobModelItem

interface GetJobsUseCase {

    suspend fun invoke(showOlditens: Boolean): List<JobModelItem>

    suspend fun invokeNexEvent() : JobModelItem

}