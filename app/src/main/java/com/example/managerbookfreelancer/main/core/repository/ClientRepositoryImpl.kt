package com.example.managerbookfreelancer.main.core.repository

import android.util.Log
import com.example.managerbookfreelancer.main.core.dataBase.dao.ClientDAO
import com.example.managerbookfreelancer.main.core.entity.ClientEntity
import com.example.managerbookfreelancer.main.core.model.ClientModelItem
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ClientRepositoryImpl @Inject constructor(
    private val dao: ClientDAO
) : ClientRepository {


    override fun fetchClient(): Flow<List<ClientModelItem>> {

        return dao.getAll().map { lisClient ->
            lisClient.map { jobs ->
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

        if (item != null) {
            return ClientModelItem(
                idClient = item.idClient,
                name = item.name,
                contact = item.contact,
                email = item.email
            )
        } else {
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

    private companion object {
        const val JOBS = "jobs"
    }

}