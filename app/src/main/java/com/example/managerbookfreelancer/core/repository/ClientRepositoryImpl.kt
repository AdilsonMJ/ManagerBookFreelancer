package com.example.managerbookfreelancer.core.repository

import com.example.managerbookfreelancer.core.dataBase.dao.ClientDAO
import com.example.managerbookfreelancer.core.entity.ClientEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ClientRepositoryImpl @Inject constructor(
    private val dao: ClientDAO
) : ClientRepository {

    override  fun fetchClient(): Flow<List<ClientEntity>> = dao.getAll()

    override suspend fun insert(professionalEntity: ClientEntity) {
        dao.insert(professionalDAO = professionalEntity)
    }

    override suspend fun delete(professionalEntity: ClientEntity) {
        dao.delete(professionalDAO = professionalEntity)
    }
}