package com.example.domainTests

import com.example.domain.Chat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Test  klasa za domensku klasu [Chat]
 * @author Lazar Jankovic
 * @see Chat
 */
class ChatTests {
    private lateinit var chat: Chat

    /**
     * Pre svakog testa se objekat Chat
     * inicijalizuje na pocetne vrednosti koje ce se koristiti u svakom testu
     */
    @BeforeEach
    fun setUp(){

        chat= Chat("1","1","1")
    }
    @Test
    fun getId_test(){
        val id=chat.getId()
        assertEquals("1",id)
    }
    @Test
    fun setId_testNull(){
        assertThrows<NullPointerException> {
            chat.setId(null)
        }
    }
    @Test
    fun setId_testPrazan(){
        assertThrows<IllegalArgumentException> {
            chat.setId("")
        }
    }
    @Test
    fun setId_ispravno(){
        chat.setId("2")
        assertEquals("2",chat.getId())
    }
    @Test
    fun getDoctorId_test(){
        val id=chat.getDoctorId()
        assertEquals("1",id)
    }
    @Test
    fun setDoctorId_testNull(){
        assertThrows<NullPointerException> {
            chat.setDoctorId(null)
        }
    }
    @Test
    fun setDoctorId_testPrazan(){
        assertThrows<IllegalArgumentException> {
            chat.setDoctorId("")
        }
    }
    @Test
    fun setDoctorId_ispravno(){
        chat.setDoctorId("2")
        assertEquals("2",chat.getDoctorId())
    }
    @Test
    fun getPatientId_test(){
        val id=chat.getPatientId()
        assertEquals("1",id)
    }
    @Test
    fun setPatientId_testNull(){
        assertThrows<NullPointerException> {
            chat.setPatientId(null)
        }
    }
    @Test
    fun setPatientId_testPrazan(){
        assertThrows<IllegalArgumentException> {
            chat.setPatientId("")
        }
    }
    @Test
    fun setPatientId_ispravno(){
        chat.setPatientId("2")
        assertEquals("2",chat.getPatientId())
    }
    @ParameterizedTest
    @CsvSource(
        "1,1,1,1,1,1,true",
        "1,1,1,2,1,1,true",
        "1,1,1,2,2,1,false"
    )
    fun equals_test(
         id1: String,  doctorId1: String,  patientId1: String
        ,id2: String, doctorId2: String, patientId2: String, equals: Boolean
    ){
        val chat1=Chat(id1,doctorId1,patientId1)
        val chat2=Chat(id2,doctorId2,patientId2)
        println(chat2)
        println(chat1)
        assertEquals(equals,chat1.equals(chat2))
    }
    @Test
    fun hashCode_test(){
        val chat1= Chat("1","1","1")
        assertEquals(chat1.hashCode(),chat.hashCode())
    }
    @Test
    fun toString_test(){
        val string=chat.toString()
        assertTrue(string.contains("Chat"))
        assertTrue(string.contains("1"))
        assertTrue(string.contains("1"))
        assertTrue(string.contains("1"))
    }

}