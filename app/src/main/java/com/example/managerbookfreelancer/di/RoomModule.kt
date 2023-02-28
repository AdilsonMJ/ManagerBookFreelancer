package com.example.managerbookfreelancer.di

import android.app.Application
import com.example.managerbookfreelancer.core.dataBase.JobAppDataBase
import com.example.managerbookfreelancer.core.dataBase.dao.ClientDAO
import com.example.managerbookfreelancer.core.dataBase.dao.JobDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {


    @Singleton
    @Provides
    fun providersRoomDataBase(application: Application): JobAppDataBase {
        return JobAppDataBase.getInstance(application)
    }


    @Singleton
    @Provides
    fun provideJobDao(dataBase: JobAppDataBase): JobDAO {
        return dataBase.JobDAO()
    }

    @Singleton
    @Provides
    fun provideClientDao(dataBase: JobAppDataBase): ClientDAO {
        return dataBase.ClientDAO()
    }

}