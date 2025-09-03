package com.example.domainTests


import com.example.domain.Role
import com.example.domain.User
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Klasa za testiraje metoda iz [User] klase
 * @property user User koji se koristi za testove
 */
class UserTests {
    var user: User?=null

    /**
     * Metoda koja pre svakog testa inicijalizuje [user],
     * kako ne bi doslo do curenja memeorije
     */
    @BeforeEach
    fun setUp(){

        user= User("1","test@test.com","Password123!", Role.ROLE_PATIENT)
    }
    /**
     * Metoda koja posle svakog testa inicijalizuje [user] na null,
     * kako ne bi doslo do curenja memeorije
     */
    @AfterEach
    fun tearDown(){
        user=null
    }

    /**
     * Test koji proverava da li je id koji vraca metoda getId jednak 1
     */
    @Test
    fun getId_Test(){
        assertEquals("1",user?.getId())

    }

    /**
     * Test koji proverava da li ako prosledimo null, metoda setId ispravno baca izuzetak
     */
    @Test
    fun setId_nullTest(){
        assertThrows<NullPointerException> { user?.setId(null) }
    }
    /**
     * Test koji proverava da li ako prosledimo prazan string, metoda setId ispravno baca izuzetak
     */
    @Test
    fun setId_prazanTest(){
        assertThrows<IllegalArgumentException> {
            user?.setId("")
        }
    }
    /**
     * Test koji proverava da li ako prosledimo ispravan parametar,
     * metoda setId ispravno inicijalizute id za vise razlicitih vrednosti
     */
    @ParameterizedTest
    @CsvSource(
        "1",
        "111greg",
        "1e23r"
    )
    fun setId_ispravanIdTest(id: String){
        user?.setId(id)
        assertEquals(id,user?.getId())
    }

    /**
     * Test koji proverava da li ako prosledimo null, metoda setEmail ispravno baca izuzetak
     */
    @Test
    fun setEmail_nullTest(){
        assertThrows<NullPointerException> { user?.setEmail(null) }
    }
    /**
     * Test koji proverava da li ako prosledimo prazan string, metoda setEmail ispravno baca izuzetak
     */
    @Test
    fun setEmail_prazanStringTest(){
        assertThrows<IllegalArgumentException> {
            user?.setEmail("")
        }
    }
    /**
     * Test koji proverava da li ako prosledimo neodgovarajuci format email-a
     * , metoda setEmail ispravno baca izuzetak
     */
    @ParameterizedTest
    @CsvSource(
        "nwewe@wemp",
        "111greg",
        "wdwfwefw@wedwfe."
    )
    fun setEmail_neodgovarajuciFormat(email: String){
        assertThrows<IllegalArgumentException> {
            user?.setEmail(email)
        }
    }
    /**
     * Test koji proverava da li ako prosledimo ispravan email, da li je isti kao dodeljeni pomocu
     * metode setEmail
     */
    @ParameterizedTest
    @CsvSource(
        "nwewe@wemp.com",
        "111greg@email.com",
        "wdwfwefw@wedwfe.com"
    )
    fun setEmail_odgovarajuciFormat(email: String){
        user?.setEmail(email)
        assertEquals(email,user?.getEmail())
    }
    /**
     * Test koji proverava da li ako prosledimo null, metoda setPassword ispravno baca izuzetak
     */
    @Test
    fun setPassword_nullTest(){
        assertThrows<NullPointerException> { user?.setEmail(null) }
    }

    /**
     * Test koji proverava da li ako prosledimo neispravan format
     * , metoda setPassword ispravno baca izuzetak
     */
    @ParameterizedTest
    @CsvSource(
        "Maaaaaa1",
        "maaaaaaa!3",
        "M!mmmmmm",
        "''"
    )
    fun setPassword_neodgovarajuciFormat(password: String){
        assertThrows<IllegalArgumentException> {
            user?.setPassword(password)
        }
    }

    /**
     * Test koji proverava da li kada prosledimo ispravne argumente metodi setPassword,
     * da li ona dodeljuje ispravan password
     */
    @ParameterizedTest
    @CsvSource(
        "Password123!",
        "Pass123!?",
        "Password12!!"
    )
    fun setPassword_odgovarajuciFormat(password: String){
        user?.setPassword(password)
        assertEquals(password,user?.getPassword())
    }

    /**
     * Test koji proverava da li kada se metodi setRole prosledi null,
     * baca izuzetak
     */
    @Test
    fun setRole_null(){
        assertThrows<NullPointerException> {
            user?.setRole(null)
        }
    }

    /**
     * Test koji proverava da li kad se prosledi ispravan argument funkciji setRole,
     * da li je isti kao dodeljeni
     */
    @Test
    fun setRole_ispravno(){
        user?.setRole(Role.ROLE_PATIENT)
        assertEquals(Role.ROLE_PATIENT,user?.getRole())

    }

    /**
     * Parametrizovani test koji proverava equals metodu,
     * i kome se prosledjuju 2 slucaja gde ce da vrati da su isti
     */
    @ParameterizedTest
    @CsvSource(
        "1,1,test@test.com,test@test.com",
        "1,2,test@test.com,test@test.com",
    )
    fun equals_isti_test(id1:String,id2: String,email1: String,email2: String){
        val user1= User(id1,email1,"Password123!",Role.ROLE_PATIENT)
        val user2= User(id2,email2,"Password123!",Role.ROLE_PATIENT)

        assertTrue(user1.equals(user2))
    }

    /**
     * Test koji proverava equals ako su objekti razliciti
     */
    @Test
    fun equals_razliciti_test(){
        val user1= User("1","user1@gmail.com","Password123!",Role.ROLE_PATIENT)
        val user2= User("2","user2@gmail.com","Password123!",Role.ROLE_PATIENT)

        assertFalse (user1.equals(user2))
    }
    /**
     * Parametrizovani test koji proverava hashCode metodu,
     * i kome se prosledjuju 2 slucaja gde ce da vrati da su isti
     */
    @ParameterizedTest
    @CsvSource(
        "1,1,test@test.com,test@test.com",
    )
    fun hashCode_Isti(id1:String,id2: String,email1: String,email2: String){
        val user1= User(id1,email1,"Password123!",Role.ROLE_PATIENT)
        val user2= User(id2,email2,"Password123!",Role.ROLE_PATIENT)
        assertTrue(
            user1.hashCode().equals(user2.hashCode())
        )
    }
    /**
     * Parametrizovani test koji proverava hashCode metodu,
     * i kome se prosledjuju 2 slucaja gde ce da vrati da su isti
     */
    @ParameterizedTest
    @CsvSource(
        "1,2,test1@test.com,test@test.com",
        "1,1,test@test.com,test2@test.com",
    )
    fun hashCode_Razliciti(id1:String,id2: String,email1: String,email2: String){
        val user1= User(id1,email1,"Password123!",Role.ROLE_PATIENT)
        val user2= User(id2,email2,"Password123!",Role.ROLE_PATIENT)
        assertFalse(user1.hashCode().equals(user2.hashCode()))
    }

    /**
     * Test koji proverava toString metodu tako sto poredi da li se svaki posebni
     * string nalazi u user.toString
     */
    @Test
    fun toString_test(){
        val string=user?.toString()?:""
        assertTrue(string.contains("1"))
        assertTrue(string.contains("test@test.com"))

        assertTrue(string.contains("ROLE_PATIENT"))

        assertTrue(string.contains("Password123!"))

    }


}