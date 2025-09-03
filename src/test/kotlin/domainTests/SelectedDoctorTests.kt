package com.example.domainTests

import com.example.domain.SelectedDoctor
import io.ktor.server.sessions.Sessions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SelectedDoctorTests {

    var selectedDoctor: SelectedDoctor?=null
    @BeforeEach
    fun setUp(){
        selectedDoctor= SelectedDoctor("1","1")

    }
    @AfterEach
    fun tearDown(){
        selectedDoctor=null
    }
    @Test
    fun getPatientIdTest(){
        assertEquals("1",selectedDoctor?.getPatientId())
    }
    @Test
    fun getDoctorIdTest(){
        assertEquals("1",selectedDoctor?.getDoctorId())
    }

    @Test
    fun setPatientId_null(){
        assertThrows<NullPointerException> {
            selectedDoctor?.setPatientId(null)
        }
    }
    @Test
    fun setPatientId_prazanString(){
        assertThrows<IllegalArgumentException> {
            selectedDoctor?.setPatientId("")
        }
    }
    @Test
    fun setPatientId_ispravno(){
        selectedDoctor?.setPatientId("2")
        assertEquals("2",selectedDoctor?.getPatientId())

    }
    @Test
    fun setDoctorId_null(){
        assertThrows<NullPointerException> {
            selectedDoctor?.setDoctorId(null)
        }
    }
    @Test
    fun setDoctorId_prazanString(){
        assertThrows<IllegalArgumentException> {
            selectedDoctor?.setDoctorId("")
        }
    }
    @Test
    fun setDoctorId_ispravno(){
        selectedDoctor?.setDoctorId("2")
        assertEquals("2",selectedDoctor?.getDoctorId())

    }
    @ParameterizedTest
    @CsvSource(
        "1,1,true",
        "1,2,false",
        "2,1,false"
    )
    fun equalsTest(id1: String,id2: String,boolean: Boolean){
        val doctor2= SelectedDoctor(id1,id2)
        assertEquals(boolean,selectedDoctor?.equals(doctor2))
    }

    @Test
    fun hashCode_test(){
        val doctor2= SelectedDoctor("1","1")
        assertEquals(selectedDoctor?.hashCode(),doctor2?.hashCode())
    }
    @Test
    fun toString_test(){
        val doctor2= selectedDoctor.toString()

        assertTrue(doctor2.contains("1"))
        assertTrue (doctor2.contains("1"))
    }
}