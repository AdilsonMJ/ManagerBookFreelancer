package com.example.managerbookfreelancer.core.repository

import com.example.managerbookfreelancer.core.dataBase.dao.ClientDAO
import com.example.managerbookfreelancer.core.entity.ClientEntity
import com.example.managerbookfreelancer.core.model.ClientModelItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ClientRepositoryImpl @Inject constructor(
    private val dao: ClientDAO
) : ClientRepository {

    override  fun fetchClient(): Flow<List<ClientModelItem>> {
      return  dao.getAll().map { lisClient ->
            lisClient.map { jobs->
                ClientModelItem(
                    idClient = jobs.idClient,
                    name = jobs.name,
                    contact = jobs.contact,
                    email = jobs.email
                )
            }
        }
    }

    override suspend fun insert(clientEntity: ClientEntity) {
        dao.insert(clientEntity = clientEntity)
    }

    override suspend fun delete(id: Long) {
        dao.delete(id)
    }
}