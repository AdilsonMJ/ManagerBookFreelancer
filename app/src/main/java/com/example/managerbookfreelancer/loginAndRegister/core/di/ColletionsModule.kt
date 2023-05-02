package com.example.managerbookfreelancer.loginAndRegister.core.di

import com.example.managerbookfreelancer.loginAndRegister.core.MotherClass.CreateUser
import com.example.managerbookfreelancer.loginAndRegister.core.MotherClass.AuthUser
import com.example.managerbookfreelancer.loginAndRegister.core.repository.AuthRepository
import com.example.managerbookfreelancer.loginAndRegister.core.repository.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AuthModule {

    @Provides
    @Singleton
    fun providesCreateUser(): CreateUser = CreateUser()

    @Provides
    @Singleton
    fun providesAuthUser(): AuthUser = AuthUser()

    @Provides
    @Singleton
    fun providesAuthRepository(
        createUser: CreateUser,
        authUser: AuthUser
    ): AuthRepository = AuthRepositoryImpl(createUser, authUser)

}