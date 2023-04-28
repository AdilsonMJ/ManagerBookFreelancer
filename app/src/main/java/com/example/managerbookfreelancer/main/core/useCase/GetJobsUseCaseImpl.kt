package com.example.managerbookfreelancer.main.core.useCase

import com.example.managerbookfreelancer.main.core.entity.JobEntity
import com.example.managerbookfreelancer.main.core.model.ClientModelItem
import com.example.managerbookfreelancer.main.core.model.JobModelItem
import com.example.managerbookfreelancer.main.core.repository.ClientRepository
import com.example.managerbookfreelancer.main.core.repository.JobsRepository
import com.example.managerbookfreelancer.main.utils.Constants.Companion.IDTOEMPTYOBJECT
import com.example.managerbookfreelancer.main.utils.Utils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GetJobsUseCaseImpl @Inject constructor(
    private val jobsRepository: JobsRepository, private val clientRepository: ClientRepository
) : GetJobsUseCase {

    private val data = Utils.getDateInMillesWithoutTime()

    override fun invoke(showOlditens: Boolean): Flow<List<JobModelItem>> {

        return jobsRepository.fetchJobs(data, showOlditens)
            .map { jobList ->
                jobList.map { job ->

                    val client: ClientModelItem = clientRepository.getClientByID(job.idClient)

                    JobModelItem(
                        idJob = job.idJob,
                        clientName = client.name,
                        date = Utils.formatDate(job.dateOfEvent),
                        time = job.timeOfEvent,
                        city = job.locationOfEvent,
                        customerEndUser = job.customerEndUser
                    )

                }
            }
    }

    override suspend fun invokeNexEvent(): JobModelItem {

        val job: JobEntity = jobsRepository.getNextJob(data)

        return if (job != null) {

            val client: ClientModelItem = clientRepository.getClientByID(job.idClient)
            JobModelItem(
                idJob = job.idJob,
                clientName = client.name,
                date = Utils.formatDate(job.dateOfEvent),
                time = job.timeOfEvent,
                city = job.locationOfEvent,
                customerEndUser = job.customerEndUser

            )
        } else {
            JobModelItem(
                idJob = IDTOEMPTYOBJECT.toLong(),
                clientName = null,
                date = "null"
            )
        }
    }
}
