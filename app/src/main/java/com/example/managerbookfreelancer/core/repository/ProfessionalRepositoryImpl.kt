package com.example.managerbookfreelancer.core.repository

import com.example.managerbookfreelancer.core.dataBase.dao.ProfessionalDAO
import com.example.managerbookfreelancer.core.entity.ProfessionalEntity
import kotlinx.coroutines.flow.Flow

class ProfessionalRepositoryImpl(
    private val dao: ProfessionalDAO
) : ProfessionalRepository {

    override fun fetchProfessional(): Flow<List<ProfessionalEntity>> = dao.getAll()

    override suspend fun insert(professionalEntity: ProfessionalEntity) {
        dao.insert(professionalDAO = professionalEntity)
    }

    override suspend fun delete(professionalEntity: ProfessionalEntity) {
        dao.delete(professionalDAO = professionalEntity)
    }
}