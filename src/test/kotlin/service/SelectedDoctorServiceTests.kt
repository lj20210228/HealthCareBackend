package com.example.service

import com.example.domain.Doctor
import com.example.domain.Patient
import com.example.domain.SelectedDoctor
import com.example.service.patient.PatientServiceInterface
import com.example.service.selectedDoctor.SelectedDoctorServiceImplementation
import com.example.service.selectedDoctor.SelectedDoctorServiceInterface
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File
import kotlin.test.assertEquals

/**
 * Test klasa za [SelectedDoctorServiceInterface]
 *
 * Ova klasa pokriva sve osnovne funkcionalnosti interfejsa [SelectedDoctorServiceInterface], uključujući:
 * 1. Dodavanje izabranog lekara za pacijenta
 * 2. Dohvatanje svih izabranih lekara za pacijenta
 * 3. Dohvatanje svih pacijenata za određenog izabranog lekara
 *
 * @author Lazar Janković
 * Zavisi od:
 * - [DoctorServiceInterface] i [PatientServiceInterface], koji su mockovani pomoću MockK
 *
 * Napomena:
 * - `coJustRun` se koristi za suspend metode koje vraćaju [Unit]
 * - `coEvery` se koristi za suspend metode koje vraćaju neku vrednost (npr. [Doctor] ili [Patient])
 * - `coVerify` se koristi za verifikaciju da su mockovane metode pozvane očekivani broj puta
 *
 * @see SelectedDoctorServiceInterface
 * @see DoctorServiceInterface
 * @see PatientServiceInterface
 * @see SelectedDoctor
 * @see Doctor
 * @see Patient
 */
class SelectedDoctorServiceTests {

     private lateinit var doctorService: DoctorServiceInterface
     private lateinit var patientService: PatientServiceInterface
     private lateinit var service: SelectedDoctorServiceImplementation
    /**
     * Setup pre svakog testa
     * - Mockuje [DoctorServiceInterface] i [PatientServiceInterface]
     * - Kreira instancu [SelectedDoctorServiceImplementation]
     * - Briše fajl `selectedDoctors.json` kako bi testovi bili izolovani
     */
    @BeforeEach
    fun setUp(){

        doctorService= mockk()
        patientService=mockk()
        service= SelectedDoctorServiceImplementation(doctorService,patientService)
        File("selectedDoctors.json").writeText("")


    }
    /**
     * Tear down posle svakog testa
     * - Briše fajl `selectedDoctors.json`
     */
    @AfterEach
    fun tearDown(){
        File("selectedDoctors.json").writeText("")
    }
    /**
     * Test dodavanja izabranog lekara za pacijenta
     * - Verifikuje da metoda vraća isti objekat koji je prosleđen
     * - Proverava da je objekat dodat u internu listu `listOfSelectedDoctors`
     * - Mockuje [editCurrentPatients] metodom [coJustRun] jer vraća [Unit]
     */
     @Test
     fun addSelectedDoctorForPatient_ispravno()= runBlocking {
         val selected= SelectedDoctor("1","1")
         coJustRun { doctorService.editCurrentPatients("1") }
         val result=service.addSelectedDoctorForPatient(selected)
         assertEquals(selected,result)
         assert(service.listOfSelectedDoctors.contains(selected))
     }
    /**
     * Test dodavanja null vrednosti
     * - Metoda treba da baci [NullPointerException]
     */
    @Test
    fun addSelectedDoctorForPatient_null() {
        runBlocking {
            assertThrows < NullPointerException>{
                service.addSelectedDoctorForPatient(null)
            }
        }
    }
    /**
     * Test dodavanja lekara koji već postoji za pacijenta
     * - Prvi put dodaje lekara uspešno
     * - Drugi put baca [IllegalArgumentException]
     */
    @Test
    fun addSelectedDoctorForPatient_vecPostoji(){
        runBlocking {
            val selectedDoctor= SelectedDoctor("1","1")
            coJustRun { doctorService.editCurrentPatients("1") }

             service.addSelectedDoctorForPatient(selectedDoctor)
            assertThrows<IllegalArgumentException> {
                service.addSelectedDoctorForPatient(selectedDoctor)
            }
        }
    }
    /**
     * Test dohvatanja izabranih lekara za pacijenta sa null id
     * - Metoda treba da baci [NullPointerException]
     */
    @Test
    fun getSelectedDoctorsForPatient_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                service.getSelectedDoctorsForPatients(null)

            }
        }

    }
    /**
     * Test dohvatanja izabranih lekara za pacijenta sa praznim id
     * - Metoda treba da baci [IllegalArgumentException]
     */
    @Test
    fun getSelectedDoctorsForPatient_prazanString(){
        runBlocking {
            assertThrows<IllegalArgumentException> {
                service.getSelectedDoctorsForPatients("")

            }
        }

    }
    /**
     * Test dohvatanja izabranih lekara za pacijenta
     * - Dodaje [SelectedDoctor] za pacijenta
     * - Mockuje [doctorService.getDoctorForId] da vrati [Doctor]
     * - Proverava da metoda vraća listu sa očekivanim doktorom
     * - Verifikuje da je metoda mockovana tačno jednom pozvana
     */
    @Test
    fun getSelectedDoctorsForPatient_ispravno(){
        runBlocking {
            val doctor= Doctor("1","1","Pera Peric","Neurolog",20,18,"1",false)
            val selected= SelectedDoctor("1","1")
            coJustRun { doctorService.editCurrentPatients("1") }
            service.addSelectedDoctorForPatient(selected)

            coEvery { doctorService.getDoctorForId("1") }returns doctor
            val result=service.getSelectedDoctorsForPatients("1")
            assertEquals(listOf<Doctor>(doctor),result)
            coVerify(exactly = 1) { doctorService.getDoctorForId("1") }
        }

    }
    /**
     * Test dohvatanja pacijenata za lekara sa null id
     * - Metoda treba da baci [NullPointerException]
     */
    @Test
    fun getSelectedPatientsForDoctor_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                service.getPatientsForSelectedDoctor(null)

            }
        }

    }
    /**
     * Test dohvatanja pacijenata za lekara sa praznim id
     * - Metoda treba da baci [IllegalArgumentException]
     */
    @Test
    fun getSelectedPatientsForDoctor_prazanString(){
        runBlocking {
            assertThrows<IllegalArgumentException> {
                service.getPatientsForSelectedDoctor("")

            }
        }

    }
    /**
     * Test dohvatanja pacijenata za lekara (happy path)
     * - Dodaje [SelectedDoctor] u internu listu
     * - Mockuje [patientService.getPatientById] da vrati [Patient]
     * - Proverava da metoda vraća listu sa očekivanim pacijentom
     * - Verifikuje da je metoda mockovana tačno jednom pozvana
     */
    @Test
    fun getSelectedPatientsForDoctor_ispravno(){
        runBlocking {
            val patient= Patient("1","1","Pera Peric","1")
            val selected= SelectedDoctor("1","1")

            service.listOfSelectedDoctors.add(selected)
            coEvery { patientService.getPatientById("1") }returns patient
            val result=service.getPatientsForSelectedDoctor("1")
            assertEquals(listOf<Patient>(patient),result)
            coVerify(exactly = 1) { patientService.getPatientById("1") }
        }

    }

}