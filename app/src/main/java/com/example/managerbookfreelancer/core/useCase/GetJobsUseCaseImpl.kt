package com.example.managerbookfreelancer.core.useCase

import com.example.managerbookfreelancer.core.entity.ClientEntity
import com.example.managerbookfreelancer.core.entity.JobEntity
import com.example.managerbookfreelancer.core.model.JobModelItem
import com.example.managerbookfreelancer.core.repository.ClientRepository
import com.example.managerbookfreelancer.core.repository.JobsRepository
import com.example.managerbookfreelancer.utils.Utils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GetJobsUseCaseImpl @Inject constructor(
    private val jobsRepository: JobsRepository, private val clientRepository: ClientRepository
) : GetJobsUseCase {

    private val time = Utils.getDateInMillesWithoutTime()

    override fun invoke(showOlditens: Boolean): Flow<List<JobModelItem>> {

        return jobsRepository.fetchJobs(time, showOlditens)
            .map { jobList ->
                jobList.map { job ->

                    val client = findClient(job.idJob)

                    JobModelItem(
                        idJob = job.idJob,
                        clientName = client.name,
                        date = Utils.formatDate(job.dateOfEvent),
                        time = job.timeOfEvent,
                        city = job.locationOfEvent
                    )

                }
            }
    }

    override suspend fun invokeNexEvent(): JobModelItem {

        val job: JobEntity = jobsRepository.getNextJob(time)


        return if (job != null) {

            val client: ClientEntity = findClient(job.idClient)

            JobModelItem(
                idJob = job.idJob,
                clientName = client.name,
                date = Utils.formatDate(job.dateOfEvent),
                time = job.timeOfEvent,
                city = job.locationOfEvent

            )} else{
                JobModelItem(
                    idJob = -1L,
                    clientName = null,
                    date = "null"
                )
            }
        }

    private suspend fun findClient(jobId: Long): ClientEntity {
        val clients = clientRepository.fetchClient().firstOrNull() ?: return ClientEntity(
            idClient = -1,
            name = "Não encontrado",
            contact = "",
            email = ""
        )

        return clients
            .filter { client -> client.idClient == jobId }
            .firstOrNull()
            ?.let { client ->
                ClientEntity(
                    idClient = client.idClient,
                    name = client.name,
                    contact = client.contact,
                    email = client.email
                )
            } ?: ClientEntity(
            idClient = -1,
            name = "Não encontrado",
            contact = "",
            email = ""
        )
    }

}