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

/**
 * Test klasa za metode [UserServiceInterface]
 * @author Lazar Janković
 * @see UserServiceInterface
 * @see User
 * @property service Instanca [UserServiceInterface] cije metode ce se koristiti
 * @property file Json fajl koji simulira tabelu u bazi
 * @property user1 Instanca klase [User]
 * @property user2 Instanca klase [User]
 * @property user3 Instanca klase [User]
 *
 */
abstract class UserServiceInterfaceTest {
    /**
     * Funckija koja sluzi za instanciranje klasa koje nasledjuju [UserServiceInterface]
     */
    abstract fun getInstance(): UserServiceInterface


    var service: UserServiceInterface?=null
    val file= File("users.json")
    private lateinit var user1: User
    private lateinit var user2: User
    private lateinit var user3: User


    /**
     * Funkcija za inicijalizaciju pre svakog testa
     */
    @BeforeEach
    fun setUp(){
        service=getInstance()
        file.writeText("")
        user1=User("1","user@user.com","Password123.", Role.ROLE_PATIENT)
        user2=User("2","user2@user.com","Password123.", Role.ROLE_DOCTOR)
        user3=User("3","user3@user.com","Password123.", Role.ROLE_PATIENT)



    }/**
     * Funkcija za inicijalizaciju posle svakog testa
     */
    @AfterEach
    fun tearDown(){
        service=null
        file.writeText("")

    }

    /**
     * Test za metodu addUser kada je prosledjeni argument null,
     * ako se baci [NullPointerException] test prolazi
     */
    @Test
    fun addUserTest_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                service?.addUser(null)
            }
        }
    }
    /**
     * Test za metodu addUser kada  prosledjeni argument vec postoji,
     * ako se baci [IllegalArgumentException] test prolazi
     */
    @Test
    fun addUserTest_VecPostoji(){
        runBlocking {
            service?.addUser(user1)
            assertThrows<IllegalArgumentException> {
                service?.addUser(user1)
            }
        }
    }
    /**
     * Test za metodu addUser kada je prosledjeni argument ispravan i ne postoji,
     * ako je vraceni objekat jednak prosledjenom test prolazi
     */
    @Test
    fun addUserTest_uspesnoDodavanje(){
        runBlocking {
            val result=service?.addUser(user1)
            assertEquals(result,user1)
        }
    }

    /**
     * Test za funkciju getUserById, kada je prosledjeni argument null,
     * ako je bacen [NullPointerException] test prolazi
     */
    @Test
    fun getUserByIdTest_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                service?.getUserById(null)
            }
        }
    }
    /**
     * Test za funkciju getUserById, kada je prosledjeni argument prazan string,
     * ako je bacen [IllegalArgumentException] test prolazi
     */
    @Test
    fun getUserByIdTest_prazanString(){
        runBlocking {
            assertThrows<IllegalArgumentException> {
                service?.getUserById("")
            }
        }
    }
    /**
     * Test za funkciju getUserById, kada je prosledjeni argument ispravan,
     * ali taj user ne postoji u bazi pa se vraca null
     */
    @Test
    fun getUserByIdTest_userNePostoji(){
        runBlocking {
            assertEquals(null,service?.getUserById("7"))
        }
    }
    /**
     * Test za funkciju getUserById, kada je prosledjeni argument ispravan,
     * i user postoji, ako je vraacen argument jednak prosledjenom test prolazi
     */
    @Test
    fun getUserByIdTest_uspesno(){
        runBlocking {
            service?.addUser(user1)
           assertEquals(user1,service?.getUserById("1"))
        }
    }
    /**
     * Test za funkciju getUserByEmail, kada je prosledjeni argument null,
     * ako je bacen [NullPointerException] test prolazi
     */
    @Test
    fun getUserByEmailTest_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                service?.getUserByEmail(null)
            }
        }
    }
    /**
     * Test za funkciju getUserByEmail, kada je prosledjeni argument prazan string,
     * ako je bacen [IllegalArgumentException] test prolazi
     */
    @Test
    fun getUserByEmailTest_prazanString(){
        runBlocking {
            assertThrows<IllegalArgumentException> {
                service?.getUserByEmail("")
            }
        }
    }
    /**
     * Test za funkciju getUserByEmail, kada je prosledjeni argument ispravan,
     * ali taj user ne postoji u bazi pa se vraca null
     */
    @Test
    fun getUserByEmailTest_userNePostoji(){
        runBlocking {
            assertEquals(null,service?.getUserByEmail("7"))
        }
    }
    /**
     * Test za funkciju getUserByEmail, kada je prosledjeni argument ispravan,
     * i user postoji, ako je vraacen argument jednak prosledjenom test prolazi
     */
    @Test
    fun getUserByEmailTest_uspesno(){
        runBlocking {
            service?.addUser(user1)
            assertEquals(user1,service?.getUserByEmail("user@user.com"))
        }
    }
    /**
     * Test za funkciju getUserByRole, kada je prosledjeni argument null,
     * ako je bacen [NullPointerException] test prolazi
     */
    @Test
    fun getUsersForRoleTest_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                service?.getUsersForRole(null)
            }
        }
    }

    /**
     * Test za funkciju getUserByRole, u slucaju kada ne postoje useri sa tom rolom pa se vraca prazna lista
     */
    @Test
    fun getUsersForRoleTest_userNePostoji(){
        runBlocking {
            service?.addUser(user2)
            assertEquals(emptyList(),service?.getUsersForRole(Role.ROLE_PATIENT))
        }
    }
    /**
     * Test za funkciju getUserByRole, u slucaju kada postoje useri sa tom rolom,
     * ako je lista koja je zadata jednaka vracenoj test prolazi
     */
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