package com.example.managerbookfreelancer.core.repository

import com.example.managerbookfreelancer.core.dataBase.dao.ClientDAO
import com.example.managerbookfreelancer.core.entity.ClientEntity

class ClientRepositoryImpl(
    private val dao: ClientDAO
) : ClientRepository {

    override suspend fun fetchClient(): List<ClientEntity> = dao.getAll()

    override suspend fun insert(professionalEntity: ClientEntity) {
        dao.insert(professionalDAO = professionalEntity)
    }

    override suspend fun delete(professionalEntity: ClientEntity) {
        dao.delete(professionalDAO = professionalEntity)
    }
}