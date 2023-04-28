package com.example.managerbookfreelancer

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.managerbookfreelancer.loginAndRegister.CreateUser
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class CreateUserTest {

    @Test
    fun testRegisterInvalid() {
        val email = "adilson@gmail.com"
        val senha = "1256"

        val createUser = CreateUser()
        
        createUser.createUser(email, senha) {
            assertFalse(it)
        }

    }

    @Test
    fun testRegisterSucess() {

        val number = Random(System.currentTimeMillis()).nextInt()

        val email = "${number}adilson@gmail.com"
        val senha = "1256"

        val createUser = CreateUser()

        createUser.createUser(email, senha) {
            assertTrue(it)
        }
    }
}

