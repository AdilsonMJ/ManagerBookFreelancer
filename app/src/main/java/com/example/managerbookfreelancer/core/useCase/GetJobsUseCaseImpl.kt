package com.example.managerbookfreelancer.core.useCase

import com.example.managerbookfreelancer.core.entity.ClientEntity
import com.example.managerbookfreelancer.core.entity.JobEntity
import com.example.managerbookfreelancer.core.model.JobModelItem
import com.example.managerbookfreelancer.core.repository.ClientRepository
import com.example.managerbookfreelancer.core.repository.JobsRepository
import com.example.managerbookfreelancer.utils.Utils


class GetJobsUseCaseImpl(
    private val jobsRepository: JobsRepository, private val clientRepository: ClientRepository
) : GetJobsUseCase {

    private val time = Utils.getDateInMillesWithoutTime()

    override suspend fun invoke(showOlditens: Boolean): List<JobModelItem> {

        val jobs = jobsRepository.fetchJobs(time, showOlditens)

        return jobs.map { job ->

            val client: ClientEntity = findClient(job.idClient)

            JobModelItem(
                idJob = job.idJob,
                clientName = client.name,
                date = Utils.formatDate(job.dateOfEvent),
                time = job.timeOfEvent,
                city = job.locationOfEvent
            )
        }
    }

    override suspend fun invokeNexEvent(): JobModelItem {

        val job: JobEntity = jobsRepository.getNextJob(time)

        if (job != null){
            val client: ClientEntity = findClient(job.idClient)

            return JobModelItem(
                idJob = job.idJob,
                clientName = client.name,
                date = Utils.formatDate(job.dateOfEvent),
                time = job.timeOfEvent,
                city = job.locationOfEvent

            )
        } else{
            return JobModelItem(
                idJob = -1L,
                date = "null",
            )
        }


    }

    private suspend fun findClient(jobId: Long): ClientEntity {


       return clientRepository.fetchClient().firstOrNull { it.idClient == jobId }?.let {
                ClientEntity(
                    idClient = it.idClient, name = it.name, contact = it.contact, email = it.email
                )
            } ?: ClientEntity(
                idClient = -1, name = "does not exist", contact = "null", email = "null"
            )
    }

}