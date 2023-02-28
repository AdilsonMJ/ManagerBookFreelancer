package com.example.managerbookfreelancer.core.useCase

import com.example.managerbookfreelancer.core.entity.JobEntity
import com.example.managerbookfreelancer.core.model.ClientModelItem
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

                    val client: ClientModelItem = findClient(job.idClient)

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

            val client: ClientModelItem = findClient(job.idClient)

            JobModelItem(
                idJob = job.idJob,
                clientName = client.name,
                date = Utils.formatDate(job.dateOfEvent),
                time = job.timeOfEvent,
                city = job.locationOfEvent

            )
        } else {
            JobModelItem(
                idJob = -1L,
                clientName = null,
                date = "null"
            )
        }
    }

    private suspend fun findClient(idClient: Long): ClientModelItem {
        val clients = clientRepository.fetchClient().firstOrNull() ?: return ClientModelItem(
            idClient = -1,
            name = "Não encontrado",
            contact = "",
            email = ""
        )

        return clients.firstOrNull { client -> client.idClient == idClient }
            ?.let { client ->
                ClientModelItem(
                    idClient = client.idClient,
                    name = client.name,
                    contact = client.contact,
                    email = client.email
                )
            } ?: ClientModelItem(
            idClient = -1,
            name = "Client not found ",
            contact = "",
            email = ""
        )
    }
}
