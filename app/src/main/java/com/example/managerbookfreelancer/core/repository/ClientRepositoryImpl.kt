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

    override suspend fun getClientByID(clientID: Long): ClientModelItem {
        val item = dao.getClientByID(clientID)

        if (item != null ){
            return ClientModelItem(
                idClient = item.idClient,
                name = item.name,
                contact = item.contact,
                email = item.email
            )
        }else{
            return ClientModelItem(
                idClient = 0,
                "Not Exist",
                "",
                ""
            )
        }

    }

    override suspend fun delete(id: Long) {
        dao.delete(id)
    }
}