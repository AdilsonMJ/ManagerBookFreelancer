package com.example.managerbookfreelancer.core.repository

import com.example.managerbookfreelancer.core.entity.ProfessionalEntity
import kotlinx.coroutines.flow.Flow

interface ProfessionalRepository {

    fun fetchProfessional() : Flow<List<ProfessionalEntity>>

    suspend fun insert(professionalEntity: ProfessionalEntity)

    suspend fun delete(professionalEntity: ProfessionalEntity)

}