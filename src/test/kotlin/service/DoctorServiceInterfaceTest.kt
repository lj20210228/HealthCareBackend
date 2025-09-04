package com.example.service

import com.example.domain.Doctor
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

abstract class DoctorServiceInterfaceTest {

    abstract fun getInstance(): DoctorServiceInterface

    var doctorService: DoctorServiceInterface?=null
    @BeforeEach
    fun setUp(){
        doctorService=getInstance()
    }
    @AfterEach
    fun tearDown(){
        doctorService=null
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

}