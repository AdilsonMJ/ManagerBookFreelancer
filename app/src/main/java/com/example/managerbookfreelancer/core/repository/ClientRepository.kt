package com.example.managerbookfreelancer.core.repository

import com.example.managerbookfreelancer.core.entity.ClientEntity
import com.example.managerbookfreelancer.core.model.ClientModelItem
import kotlinx.coroutines.flow.Flow

interface ClientRepository {

     fun fetchClient(): Flow<List<ClientModelItem>>

    suspend fun insert(clientEntity: ClientEntity)

    suspend fun getClientByID(clientID: Long) : ClientModelItem

    suspend fun delete(id: Long)

}