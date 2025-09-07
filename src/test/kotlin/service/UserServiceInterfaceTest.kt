package com.example.service

import com.example.domain.Role
import com.example.domain.User
import com.example.service.user.UserServiceInterface
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

abstract class UserServiceInterfaceTest {
    abstract fun getInstance(): UserServiceInterface

    var service: UserServiceInterface?=null
    val file= File("users.json")
    private lateinit var user1: User
    private lateinit var user2: User
    private lateinit var user3: User



    @BeforeEach
    fun setUp(){
        service=getInstance()
        file.writeText("")
        user1=User("1","user@user.com","Password123.", Role.ROLE_PATIENT)
        user2=User("2","user2@user.com","Password123.", Role.ROLE_DOCTOR)
        user3=User("3","user3@user.com","Password123.", Role.ROLE_PATIENT)



    }
    @AfterEach
    fun tearDown(){
        service=null
        file.writeText("")

    }
    @Test
    fun addUserTest_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                service?.addUser(null)
            }
        }
    }
    @Test
    fun addUserTest_VecPostoji(){
        runBlocking {
            service?.addUser(user1)
            assertThrows<IllegalArgumentException> {
                service?.addUser(user1)
            }
        }
    }
    @Test
    fun addUserTest_uspesnoDodavanje(){
        runBlocking {
            val result=service?.addUser(user1)
            assertEquals(result,user1)
        }
    }
    @Test
    fun getUserByIdTest_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                service?.getUserById(null)
            }
        }
    }
    @Test
    fun getUserByIdTest_prazanString(){
        runBlocking {
            assertThrows<IllegalArgumentException> {
                service?.getUserById("")
            }
        }
    }
    @Test
    fun getUserByIdTest_userNePostoji(){
        runBlocking {
            assertEquals(null,service?.getUserById("7"))
        }
    }
    @Test
    fun getUserByIdTest_uspesno(){
        runBlocking {
            service?.addUser(user1)
           assertEquals(user1,service?.getUserById("1"))
        }
    }
    @Test
    fun getUserByEmailTest_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                service?.getUserByEmail(null)
            }
        }
    }
    @Test
    fun getUserByEmailTest_prazanString(){
        runBlocking {
            assertThrows<IllegalArgumentException> {
                service?.getUserByEmail("")
            }
        }
    }
    @Test
    fun getUserByEmailTest_userNePostoji(){
        runBlocking {
            assertEquals(null,service?.getUserByEmail("7"))
        }
    }
    @Test
    fun getUserByEmailTest_uspesno(){
        runBlocking {
            service?.addUser(user1)
            assertEquals(user1,service?.getUserByEmail("user@user.com"))
        }
    }
    @Test
    fun getUsersForRoleTest_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                service?.getUsersForRole(null)
            }
        }
    }

    @Test
    fun getUsersForRoleTest_userNePostoji(){
        runBlocking {
            service?.addUser(user2)
            assertEquals(emptyList(),service?.getUsersForRole(Role.ROLE_PATIENT))
        }
    }
    @Test
    fun getUsersForRoleTest_uspesno(){
        runBlocking {
            service?.addUser(user1)
            service?.addUser(user2)
            service?.addUser(user3)
            val list=listOf(user1,user3)
            assertEquals(list,service?.getUsersForRole(Role.ROLE_PATIENT))
        }
    }



}