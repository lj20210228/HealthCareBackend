package com.example.service

import com.example.domain.Doctor
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File
import kotlin.test.assertEquals

abstract class DoctorServiceInterfaceTest {

    abstract fun getInstance(): DoctorServiceInterface

    var doctorService: DoctorServiceInterface?=null
    val file=File("doctors.json")
    @BeforeEach
    fun setUp(){
        doctorService=getInstance()
    }
    @AfterEach
    fun tearDown(){
        doctorService=null
        file.writeText("")

    }
    @Test
     fun addDoctorTest_null(){
         runBlocking {
             assertThrows<NullPointerException> {
                 doctorService?.addDoctor(null)
             }
         }
    }

    @Test
    fun addDoctorTest_vec_postoji(){
        val doctor1= Doctor("5","5","Pera Peric","Neurolog",20,0,"1",false)
        val doctor2= Doctor("5","5","Pera Peric","Neurolog",20,0,"1",false)

        runBlocking {
            doctorService?.addDoctor(doctor1)
            assertThrows<IllegalArgumentException> {
                doctorService?.addDoctor(doctor2)
            }
        }
    }

    @Test
    fun addDoctorTest_uspesno(){
        val doctor= Doctor("1","1","Pera Peric","Neurolog",20,0,"1",false)

        runBlocking {
            val added=doctorService?.addDoctor(doctor)
            assertEquals(added,doctorService?.getDoctorForId("1"))
        }
    }
    @Test
    fun getDoctorForIdTest_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                doctorService?.getDoctorForId(null)
            }
        }
    }
    @Test
    fun getDoctorForIdTest_PrazanString(){
        runBlocking {
            assertThrows<NullPointerException> {
                doctorService?.getDoctorForId("")
            }
        }
    }
    @Test
    fun getDoctorForIdTest_ispravanId(){
        runBlocking {
            val doctor= Doctor("7","7","Pera Peric","Neurolog",20,0,"1",false)
            doctorService?.addDoctor(doctor)
            assertEquals(doctor,doctorService?.getDoctorForId("7"))

        }
    }
    @Test
    fun getAllDoctorsInHospitalTest_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                doctorService?.getAllDoctorsInHospital(null)
            }
        }
    }
    @Test
    fun getAllDoctorInHospitalTest_PrazanString(){
        runBlocking {
            assertThrows<NullPointerException> {
                doctorService?.getDoctorForId("")
            }
        }
    }
    @Test
    fun getAllDoctorsInHospitalTest_ispravanId(){
        runBlocking {
            val doctor1= Doctor("7","7","Pera Peric","Neurolog",20,0,"1",false)
            val doctor2= Doctor("1","1","Pera Mikic","Dermatolog",20,0,"1",false)
            val doctor3= Doctor("2","2","Jovan Jovic","Lekar opste prakse",20,0,"1",false)


            doctorService?.addDoctor(doctor1)
            doctorService?.addDoctor(doctor2)
            doctorService?.addDoctor(doctor3)
            val doctorList=mutableListOf<Doctor>()
            doctorList.add(doctor1)
            doctorList.add(doctor2)
            doctorList.add(doctor3)

            assertEquals(doctorList,doctorService?.getAllDoctorsInHospital("1"))

        }
    }
    @Test
    fun getAllGeneralDoctorsInHospitalTest_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                doctorService?.getAllGeneralDoctorsInHospital(null)
            }
        }
    }
    @Test
    fun getAllGeneralDoctorInHospitalTest_PrazanString(){
        runBlocking {
            assertThrows<NullPointerException> {
                doctorService?.getAllGeneralDoctorsInHospital("")
            }
        }
    }
    @Test
    fun getAllGeneralDoctorsInHospital_praznaLista(){
        runBlocking {
            assertEquals(emptyList(),doctorService?.getAllGeneralDoctorsInHospital("1"))
        }
    }

    @Test
    fun getAllGeneralDoctorsInHospitalTest_ispravanId(){
        runBlocking {
            val doctor1= Doctor("7","7","Pera Peric","Neurolog",20,0,"1",false)
            val doctor2= Doctor("1","1","Pera Mikic","Dermatolog",20,0,"1",false)
            val doctor3= Doctor("2","2","Jovan Jovic","Lekar opste prakse",20,0,"1",true)

           doctorService?.addDoctor(doctor1)
           doctorService?.addDoctor(doctor2)
           doctorService?.addDoctor(doctor3)
            val doctorList=listOf(doctor3)

            assertEquals(doctorList,doctorService?.getAllGeneralDoctorsInHospital("1"))

        }
    }

}