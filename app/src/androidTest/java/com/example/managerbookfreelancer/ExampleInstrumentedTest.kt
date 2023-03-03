package com.example.managerbookfreelancer

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.managerbookfreelancer.core.dataBase.JobAppDataBase
import com.example.managerbookfreelancer.core.dataBase.dao.ClientDAO
import com.example.managerbookfreelancer.core.dataBase.dao.JobDAO
import com.example.managerbookfreelancer.core.entity.ClientEntity
import com.example.managerbookfreelancer.core.entity.JobEntity
import com.example.managerbookfreelancer.utils.Utils
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

private const val FAKE_ID_CLIENT = 99L
private const val FAKE_ID_JOB = 88L
private const val FAKE_DATE = 2187648000000L // 29/04/2039


@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    lateinit var db: JobAppDataBase
    lateinit var clientDao: ClientDAO
    lateinit var jobDao: JobDAO


    @Before
    fun setUp() = runBlocking {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            JobAppDataBase::class.java
        ).build()

        clientDao = db.ClientDAO()

        jobDao = db.JobDAO()

        clientDao.insert(
            ClientEntity(
                idClient = FAKE_ID_CLIENT,
                name = "Adilson",
                contact = "99999",
                email = "adilson@gmail.com"
            )
        )

        jobDao.insert(
            JobEntity(
                idClient = FAKE_ID_CLIENT,
                idJob = FAKE_ID_JOB,
                customerEndUser = "Fe e Jose",
                dateOfEvent = FAKE_DATE,
                timeOfEvent = "11:50 AM",
                locationOfEvent = "Belo Horizonte"

            )
        )


    }

    @Test
    fun testInsertANewClientOnDataBase() = runBlocking {


        //when
        val result = clientDao.getClientByID(FAKE_ID_CLIENT).idClient
        //then
        assertEquals(result, FAKE_ID_CLIENT)

    }

    @Test
    fun testInserANewJobOnDataBase() = runBlocking {


        val result = jobDao.getJobById(FAKE_ID_JOB).idJob
        assertEquals(result, FAKE_ID_JOB)

    }

    @Test
    fun ifTryGetAClientNotExist() = runBlocking {
        val result: ClientEntity? = clientDao.getClientByID(32323)
        assert(result == null)
    }

    @Test
    fun ifTryGetAClientExistNotReturnNull() = runBlocking {
        val result: ClientEntity? = clientDao.getClientByID(FAKE_ID_CLIENT)
        assert(result?.idClient == FAKE_ID_CLIENT)
    }

    @Test
    fun testGetAllJobsDataBase() = runBlocking {

        //when
        val result = jobDao.getAll(Utils.getDateInMillesWithoutTime(), false).first()
        //then
        assertEquals(1, result.size)

    }


    @After
    fun closerDB() {
        db.close()
    }


}