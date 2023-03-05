package com.example.managerbookfreelancer.core.di

import com.example.managerbookfreelancer.core.repository.ClientRepository
import com.example.managerbookfreelancer.core.repository.JobsRepository
import com.example.managerbookfreelancer.core.useCase.GetJobsUseCase
import com.example.managerbookfreelancer.core.useCase.GetJobsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object ColletionsModule {

    @Singleton
    @Provides
    fun providesGetUseCase(
        jobsRepository: JobsRepository,
        clientRepository: ClientRepository
    ): GetJobsUseCase{
        return GetJobsUseCaseImpl(jobsRepository, clientRepository)
    }

}