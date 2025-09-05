package com.example.service

import com.example.domain.Patient
import com.example.service.patient.PatientServiceInterface
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File
import kotlin.test.assertEquals

abstract class PatientServiceInterfaceTest {

    var patientService: PatientServiceInterface?=null
    abstract fun getInstance(): PatientServiceInterface
    @BeforeEach
    fun setUp(){
        patientService=getInstance()
    }
    @AfterEach
    fun tearDown(){
        patientService=null
        File("patients.json").writeText("")
    }
    @Test
    fun addPatient_testNull(){
        runBlocking {
            assertThrows<NullPointerException> {
                patientService?.addPatient(null)
            }
        }

    }
    @Test
    fun addPatient_test_vec_postoji(){
        val patient1= Patient("1","1","Pera Peric","1")
        val patient2= Patient("1","1","Pera Peric","1")

        runBlocking {
            patientService?.addPatient(patient1)
            assertThrows<IllegalArgumentException> {
                patientService?.addPatient(patient2)
            }
        }
    }
    @Test
    fun addPatient_ispravno(){
        val patient1= Patient("1","1","Pera Peric","1")
        runBlocking {
            patientService?.addPatient(patient1)
            assertEquals(patient1,patientService?.getPatientById("1"))
        }
    }
    @Test
    fun getPatientById_test_null(){
        runBlocking {
            assertThrows < NullPointerException>{
                patientService?.getPatientById(null)
            }
        }

    }
    @Test
    fun getPatientById_test_PrazanString(){
        runBlocking {
            assertThrows < IllegalArgumentException>{
                patientService?.getPatientById("")
            }
        }

    }
    @Test
    fun getPatientById_vracaSeNull() {
        runBlocking {
            assertEquals(null,patientService?.getPatientById("1"))
        }
    }
    @Test
    fun getPatientById_ispravno(){
        val patient1= Patient("1","1","Pera Peric","1")
        runBlocking {
            patientService?.addPatient(patient1)
            assertEquals(patient1,patientService?.getPatientById("1"))
        }
    }
    @Test
    fun getAllPatientInHospital_test_id_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                patientService?.getAllPatientInHospital(null)
            }
        }
    }
    @Test
    fun getAllPatientInHospital_test_id_prazan(){
        runBlocking {
            assertThrows<IllegalArgumentException> {
                patientService?.getAllPatientInHospital("")
            }
        }
    }
    @Test
    fun getAllPatientInHospital_test_vracaSePraznaLista(){
        runBlocking {
          assertEquals(emptyList(),patientService?.getAllPatientInHospital("1"))
        }
    }
    @Test
    fun getAllPatientInHospital_test_vracaSeIspravno(){
        runBlocking {
            val patient1= Patient("1","1","Pera Peric","1")
            val patient2= Patient("2","2","Mika Mikic","1")
            patientService?.addPatient(patient1)
            patientService?.addPatient(patient2)
            val patients=listOf<Patient>(patient1,patient2)
            assertEquals(patients,patientService?.getAllPatientInHospital("1"))
        }
    }
}