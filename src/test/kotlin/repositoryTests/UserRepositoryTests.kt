package com.example.repositoryTests

import com.example.database.DatabaseFactory
import com.example.domain.Role
import com.example.domain.User
import com.example.repository.user.UserRepositoryImplementation
import com.example.response.BaseResponse
import com.example.response.ListResponse
import com.example.service.user.UserServiceImplementation
import com.example.service.user.UserServiceInterface
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class UserRepositoryTests {

    private lateinit var service: UserServiceInterface
    private lateinit var repository: UserRepositoryImplementation

    private val testUser = User( email = "test@example.com", role = Role.ROLE_PATIENT,
        password = "Password123!")

    @BeforeEach
    fun setUp() {
        service = UserServiceImplementation()
        repository = UserRepositoryImplementation(service)
        DatabaseFactory.connectToDb()
        DatabaseFactory.init()
        DatabaseFactory.clearTable("users")

    }
    @AfterEach
    fun tearDown(){


        DatabaseFactory.clearTable("users")

    }


    /**
     * Test za metodu za dodavanje korisnika kad user vec postoji
     */
    @Test
    fun addUser_testAlreadyExist()=runBlocking {
        service.addUser(testUser)
        val result = repository.addUser(testUser)
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Korisnik vec postoji", (result as BaseResponse.ErrorResponse).message)
    }
    /**
     * Test za metodu za dodavanje korisnika kad je prosledjen null parametar
     */
    @Test
    fun addUser_test_prosledjenNull ()= runBlocking {
        val result = repository.addUser(null)
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Niste prosledili podatke o korisniku", (result as BaseResponse.ErrorResponse).message)
    }

    /**
     * Test za metodu za dodavanje korisnika kad je uspesno dodat
     */
    @Test
    fun addUser_test_UspesnoDodat ()= runBlocking {
        val result = repository.addUser(testUser)
        assertTrue(result is BaseResponse.SuccessResponse)
        assertEquals(testUser, (result as BaseResponse.SuccessResponse).data)
        assertEquals("Korisnik uspesno dodat", result.message)

    }

    /**
     * Metoda za pronalazenje korisnika kad je prosledjen null parametar
     */
    @Test
    fun `getUserById returns error if id is null`() = runBlocking {
        val result = repository.getUserById(null)
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Niste prosledili ispravne podatke",(result as BaseResponse.ErrorResponse).message)
    }

    @Test
    fun `getUserById returns user if don't exists`() = runBlocking {

        val result = repository.getUserById("9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9")
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Korisnik nije pronadjen", (result as BaseResponse.ErrorResponse).message)
    }
    @Test
    fun `getUserById returns user if  exists`() = runBlocking {
        val user=repository.addUser(user = testUser)
        val result = repository.getUserById((user as BaseResponse.SuccessResponse).data?.getId())
        assertTrue(result is BaseResponse.SuccessResponse)
        assertEquals(testUser, (result as BaseResponse.SuccessResponse).data)
    }

    @Test
    fun `getUserByEmail returns error if email is null`() = runBlocking {
        val result = repository.getUserByEmail(null)
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Niste prosledili ispravan",(result as BaseResponse.ErrorResponse).message)
    }
    @Test
    fun `getUserByEmail returns user if_dont exists`() = runBlocking {
        val result = repository.getUserByEmail(testUser.getEmail())
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Korisnik nije pronadjen", (result as BaseResponse.ErrorResponse).message)
    }

    @Test
    fun `getUserByEmail returns user if exists`() = runBlocking {
        service.addUser(testUser)
        val result = repository.getUserByEmail(testUser.getEmail())
        assertTrue(result is BaseResponse.SuccessResponse)
        assertEquals(testUser, (result as BaseResponse.SuccessResponse).data)
    }

    @Test
    fun `getUsersForRole returns error if role is null`() = runBlocking {
        val result = repository.getUsersForRole(null)
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Niste prosledili ispravne podatke",(result as ListResponse.ErrorResponse).message)
    }

    @Test
    fun `getUsersForRole returns users if they dont exist`() = runBlocking {
        val result = repository.getUsersForRole(Role.ROLE_PATIENT)
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Korisnici nisu pronadjeni", (result as ListResponse.ErrorResponse).message)
    }
    @Test
    fun `getUsersForRole returns users if they exist`() = runBlocking {
        service.addUser(testUser)
        val result = repository.getUsersForRole(Role.ROLE_PATIENT)
        assertTrue(result is ListResponse.SuccessResponse)
        assertEquals(1, (result as ListResponse.SuccessResponse).data?.size)
    }
}