package com.example.managerbookfreelancer.main.di

import com.example.managerbookfreelancer.main.core.dataBase.dao.ClientDAO
import com.example.managerbookfreelancer.main.core.dataBase.dao.JobDAO
import com.example.managerbookfreelancer.main.core.repository.ClientRepository
import com.example.managerbookfreelancer.main.core.repository.ClientRepositoryImpl
import com.example.managerbookfreelancer.main.core.repository.JobsRepository
import com.example.managerbookfreelancer.main.core.repository.JobsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
     fun providesJobRepository(jobDao: JobDAO): JobsRepository = JobsRepositoryImpl(jobDao)


    @Provides
    @Singleton
     fun providesClientRepository(clientDAO: ClientDAO): ClientRepository = ClientRepositoryImpl(clientDAO)

}