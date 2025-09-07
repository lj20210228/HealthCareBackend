package com.example.service

import com.example.domain.Hospital
import com.example.service.hospital.HospitalServiceImplementation
import com.example.service.hospital.HospitalServiceInterface
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.io.File
import kotlin.test.assertEquals

class HospitalServiceInterfaceTest {
     var hospitalService: HospitalServiceInterface?=null
    val file= File("hospitals.json")
    @BeforeEach
    fun setUp(){
        hospitalService= HospitalServiceImplementation()
        file.writeText("")
    }
    @AfterEach
    fun tearDown(){
        file.writeText("")

        hospitalService=null
    }
    @Test
    fun addHospitalTest_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                hospitalService?.addHospital(null)
            }
        }

    }
    @ParameterizedTest
    @CsvSource(
        "1,Opsta Bolnica Cacak,Cacak,Kneza Milosa 44",
        "1,Opsta Bolnica Sabac,Sabac,Kneza Milosa 44"
        )
    fun addHospitalTest_vecPostoji(
        hospitalId:String,name: String,city: String,address: String
    ){
        runBlocking {
            val hospital= Hospital(hospitalId,name,city,address)
            val result=hospitalService?.addHospital(hospital)
            assertEquals(result,hospital)
            assertThrows<IllegalArgumentException> {
                hospitalService?.addHospital(hospital)
            }
        }

    }
    @Test
    fun addHospitalTest_uspesno(){
        val hospital= Hospital("1","Opsta bolnica Uzice","Uzice","Milosa Obrenovica 12")
        runBlocking {
            val result=hospitalService?.addHospital(hospital)
            assertEquals(result,hospital)
        }
    }
    @Test
    fun getHospitalByIdTest_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                hospitalService?.getHospitalById(null)
            }
        }
    }
    @Test
    fun getHospitalByIdTest_prazanString(){
        runBlocking {
            assertThrows<IllegalArgumentException> {
                hospitalService?.getHospitalById("")
            }
        }
    }
    @Test
    fun getHospitalByIdTest_bolnicaNePostoji(){
        runBlocking {
            assertEquals(null,hospitalService?.getHospitalByName("Opsta bolnica Kragujevac"))
        }
    }
    @Test
    fun getHospitalByIdTest_bolnicaPostoji(){
        val hospital= Hospital("1","Opsta bolnica Uzice","Uzice","Milosa Obrenovica 12")

        runBlocking {
            val result=hospitalService?.addHospital(hospital)
            assertEquals(result,hospitalService?.getHospitalById("1"))
        }
    }
    @Test
    fun getHospitalByNameTest_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                hospitalService?.getHospitalByName(null)
            }
        }
    }
    @Test
    fun getHospitalByNameTest_prazanString(){
        runBlocking {
            assertThrows<IllegalArgumentException> {
                hospitalService?.getHospitalByName("")
            }
        }
    }
    @Test
    fun getHospitalByNameTest_bolnicaNePostoji(){
        runBlocking {
            assertEquals(null,hospitalService?.getHospitalByName("Sabac"))
        }
    }
    @Test
    fun getHospitalByNameTest_bolnicaPostoji(){
        val hospital= Hospital("1","Opsta bolnica Uzice","Uzice","Milosa Obrenovica 12")

        runBlocking {
            val result=hospitalService?.addHospital(hospital)
            assertEquals(result,hospitalService?.getHospitalByName("Opsta bolnica Uzice"))
        }
    }
    @Test
    fun getHospitalsInCityTest_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                hospitalService?.getHospitalsInCity(null)
            }
        }
    }
    @Test
    fun getHospitalsInCityTest_prazanString(){
        runBlocking {
            assertThrows<IllegalArgumentException> {
                hospitalService?.getHospitalsInCity("")
            }
        }
    }
    @Test
    fun getHospitalsInCityTest_bolnicaNePostoji(){
        runBlocking {
            assertEquals(null,hospitalService?.getHospitalsInCity("Sabac"))
        }
    }
    @Test
    fun getHospitalsInCityTest_bolnicaPostoji(){
        val hospital= Hospital("1","Opsta bolnica Uzice","Uzice","Milosa Obrenovica 12")
        val hospital2= Hospital("2","Dom Zdravlja Uzice Uzice","Uzice","Ustanicka 2")

        runBlocking {
            hospitalService?.addHospital(hospital)
            hospitalService?.addHospital(hospital2)
            val list=listOf(hospital,hospital2)


            assertEquals(list,hospitalService?.getHospitalsInCity("Uzice"))
        }
    }
    @Test
    fun getAllHospitals_nemaBolnica(){


        runBlocking {



            assertEquals(emptyList(),hospitalService?.getAllHospitals())
        }
    }
    @Test
    fun getAllHospitals_bolnicaPostoji(){
        val hospital= Hospital("1","Opsta bolnica Uzice","Uzice","Milosa Obrenovica 12")
        val hospital2= Hospital("2","Dom Zdravlja Uzice Uzice","Uzice","Ustanicka 2")

        runBlocking {
            hospitalService?.addHospital(hospital)
            hospitalService?.addHospital(hospital2)
            val list=listOf(hospital,hospital2)


            assertEquals(list,hospitalService?.getAllHospitals())
        }
    }
}