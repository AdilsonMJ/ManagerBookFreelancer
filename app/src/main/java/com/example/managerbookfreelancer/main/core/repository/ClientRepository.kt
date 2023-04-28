package com.example.managerbookfreelancer.main.core.repository

import com.example.managerbookfreelancer.main.core.entity.ClientEntity
import com.example.managerbookfreelancer.main.core.model.ClientModelItem
import kotlinx.coroutines.flow.Flow

interface ClientRepository {

     fun fetchClient(): Flow<List<ClientModelItem>>

    suspend fun insert(clientEntity: ClientEntity)

    suspend fun getClientByID(clientID: Long) : ClientModelItem

    suspend fun delete(id: Long)

}