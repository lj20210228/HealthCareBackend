package com.example.service

import com.example.domain.Patient
import com.example.service.patient.PatientServiceInterface
import io.mockk.coEvery
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File
import kotlin.test.assertEquals

/**
 * Test klasa za testiranje [PatientServiceInterface]
 * @property patientService Instanca interfejsa [PatientServiceInterface]
 * @property file Fajl koji simulira bazu
 * @author Lazar Janković
 * @see PatientServiceInterface
 */
abstract class PatientServiceInterfaceTest {

    var patientService: PatientServiceInterface?=null
    val file= File("patients.json")

    /**
     * Funkcija za odredjivanje koja tacno klasa nasledjuje interfejs
     */
    abstract fun getInstance(): PatientServiceInterface

    /**
     * Pre svakog testa se inicijalizuje [patientService]
     */
    @BeforeEach
    fun setUp(){
        patientService=getInstance()
        file.writeText("")
    }
    /**
     * Pre svakog testa se inicijalizuje [patientService] na null,
     * i brisu se svi podaci iz fajla, koji simulira tabelu u bazi
     */
    @AfterEach
    fun tearDown(){
        patientService=null
        file.writeText("")

    }

    /**
     * Dodavanje pacijenta koji ima null vrednost,
     * ako metode baci [NullPointerException] test prolazi
     */
    @Test
    fun addPatient_testNull(){
        runBlocking {
            assertThrows<NullPointerException> {
                patientService?.addPatient(null)
            }
        }

    }
    /**
     * Dodavanje pacijenta koji vec postoji,
     * ako metode baci [IllegalArgumentException] test prolazi
     */
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

    /**
     * Metoda za uspesno dodavanje pacijenta, ako je rezultat metode addPatient jednak prosledjenom arguentu test prolazi
     */
    @Test
    fun addPatient_ispravno(){
        val patient1= Patient("1","1","Pera Peric","1")
        runBlocking {
            val result = patientService!!.addPatient(patient1)

            assertEquals(patient1,result)

        }
    }

    /**
     * Test metode za trazenje pacijenta kada je prosledjeni argument null, ako metoda baci
     * [NullPointerException] test prolazi
     */
    @Test
    fun getPatientById_test_null(){
        runBlocking {
            assertThrows < NullPointerException>{
                patientService?.getPatientById(null)
            }
        }

    }
    /**
     * Test metode za trazenje pacijenta kada je prosledjeni argument prazan, ako metoda baci
     * [IllegalArgumentException] test prolazi
     */
    @Test
    fun getPatientById_test_PrazanString(){
        runBlocking {
            assertThrows < IllegalArgumentException>{
                patientService?.getPatientById("")
            }
        }

    }

    /**
     * Metoda koja testira slusaj kada pacijent nije pronadjen pa metoda vraca null vrednost
     */
    @Test
    fun getPatientById_vracaSeNull() {
        runBlocking {
            assertEquals(null,patientService?.getPatientById("1"))
        }
    }

    /**
     * Metoda koja testira da li se pacijent ispravno pronalazi, ako je rezultat addPatientJednak rezultatu
     * metode get, test prolazi
     */
    @Test
    fun getPatientById_ispravno(){
        val patient1= Patient("1","1","Pera Peric","1")
        runBlocking {
            val result = patientService!!.addPatient(patient1)

            assertEquals(patient1,result)
        }
    }
    /**
     * Test metode za trazenje pacijenta kada je prosledjeni argument null, ako metoda baci
     * [NullPointerException] test prolazi
     */
    @Test
    fun getAllPatientInHospital_test_id_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                patientService?.getAllPatientInHospital(null)
            }
        }
    }
    /**
     * Test metode za trazenje pacijenta kada je prosledjeni argument prazan, ako metoda baci
     * [IllegalArgumentException] test prolazi
     */
    @Test
    fun getAllPatientInHospital_test_id_prazan(){
        runBlocking {
            assertThrows<IllegalArgumentException> {
                patientService?.getAllPatientInHospital("")
            }
        }
    }
    /**
     * Metoda koja testira slusaj kada pacijenti nisu pronadjeni pa metoda vraca praznu listu
     */
    @Test
    fun getAllPatientInHospital_test_vracaSePraznaLista(){
        runBlocking {
          assertEquals(emptyList(),patientService?.getAllPatientInHospital("1"))
        }
    }
    /**
     * Metoda koja testira da li se pacijentu ispravno pronalaze, ako je lokalna lista jednaka onoj
     * u [patientService] test prolazi
     */
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