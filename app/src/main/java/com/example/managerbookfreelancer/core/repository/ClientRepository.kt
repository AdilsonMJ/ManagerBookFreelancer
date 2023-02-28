package com.example.managerbookfreelancer.core.repository

import com.example.managerbookfreelancer.core.entity.ClientEntity
import kotlinx.coroutines.flow.Flow

interface ClientRepository {

     fun fetchClient(): Flow<List<ClientEntity>>

    suspend fun insert(professionalEntity: ClientEntity)

    suspend fun delete(professionalEntity: ClientEntity)

}