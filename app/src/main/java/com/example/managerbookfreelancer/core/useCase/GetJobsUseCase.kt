package com.example.managerbookfreelancer.core.useCase

import com.example.managerbookfreelancer.core.model.JobModelItem
import kotlinx.coroutines.flow.Flow

interface GetJobsUseCase {

     fun invoke(showOlditens: Boolean): Flow<List<JobModelItem>>

    suspend fun invokeNexEvent() : JobModelItem

}