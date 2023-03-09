package com.example.managerbookfreelancer.main.core.useCase

import com.example.managerbookfreelancer.main.core.model.JobModelItem
import kotlinx.coroutines.flow.Flow

interface GetJobsUseCase {

     fun invoke(showOlditens: Boolean): Flow<List<JobModelItem>>

    suspend fun invokeNexEvent() : JobModelItem

}