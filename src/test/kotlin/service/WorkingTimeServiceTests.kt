package com.example.service

import com.example.domain.DayInWeek
import com.example.domain.WorkTime
import com.example.service.workTime.WorkTimeInterface
import com.example.service.workTime.WorkTimeServiceImplementation
import io.ktor.http.buildUrl
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File
import java.time.LocalTime
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Testovi za funkcije iz [WorkTimeInterface]
 * @author Lazar Janković
 * @see WorkTimeInterface
 * @see WorkTime
 * @property service Interfejs koji je na pocetku inicijalizovan na null
 * @property workTime Instanca klase [WorkTime] koja ce se koristiti u testovima
 * @property file Fajl koji simulirak kolonu u bazi
 */
class WorkingTimeServiceTests {

    private var service: WorkTimeInterface?=null
    private var workTime: WorkTime?=null
    var file: File?=null

    /**
     * Funckija koja na pocetku inicijalizuje [service], [file],[workTime] i koja se izvrsava pre svakog testa
     */
    @BeforeEach
    fun setUp(){
        service= WorkTimeServiceImplementation()
        file=File("workTime.json")
        file?.writeText("")
        workTime= WorkTime("1", LocalTime.of(8,0), LocalTime.of(15,0),"1", DayInWeek.MONDAY)

    }
    /**
     * Funckija koja inicijalizuje [service], [file],[workTime] na null i koja se izvrsava posle svakog testa
     */
    @AfterEach
    fun tearDown(){
        service=null
        workTime=null
        file?.writeText("")

    }

    /**
     * Funckija koja testira metodu getWorkTimeForDoctorId, kada je prosledjeni parametar null
     * @throws NullPointerException ako je bacen izuzetak, test prolazi
     */
    @Test
    fun getWorkTimeForDoctorId_testNull(){
        runBlocking {

            assertThrows<NullPointerException> {
                service?.getWorkTimeForDoctor(null)
            }
        }
    }
    /**
     * Funckija koja testira metodu getWorkTimeForDoctorId, kada je prosledjeni parametar prazan
     * @throws IllegalArgumentException ako je bacen izuzetak, test prolazi
     */
    @Test
    fun getWorkTimeForDoctorId_testPrazan(){
        runBlocking {

            assertThrows<IllegalArgumentException> {
                service?.getWorkTimeForDoctor("")
            }
        }
    }
    /**
     * Funckija koja testira metodu getWorkTimeForDoctorId, kada je prosledjeni parametar id lekara kog nema u bazi
     * @return null ako je vracena null vrednost test prolazi
     */
    @Test
    fun getWorkTimeForDoctorId_testNemaLekara(){
        runBlocking {
            assertEquals(null,service?.getWorkTimeForDoctor("3"))
        }
    }
    /**
     * Funckija koja testira metodu getWorkTimeForDoctorId, kada je prosledjeni parametar ispravan id kog ima u bazi
     * @return [WorkTime] Vracaju se podaci o radnom vremenu lekara
     */
    @Test
    fun getWorkTimeForDoctorId_test(){
        runBlocking {
            service?.addWorkTime(workTime)
            assertTrue(service?.getWorkTimeForDoctor("1")?.contains(workTime)==true)

        }
    }

    /**
     * Funckija koja testira metodu addWorkTime, kada je prosledjeni parametar null
     * @throws NullPointerException ako je bacen izuzetak, test prolazi
     */
    @Test
    fun addWorkTime_test_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                service?.addWorkTime(null)
            }
        }
    }
    /**
     * Funckija koja testira metodu addWorkTime, kada prodledjeni parametar vec postoji
     * @throws IllegalArgumentException ako je bacen izuzetak, test prolazi
     */
    @Test
    fun addWorkTime_test_VecPostoji(){
        runBlocking {
            service?.addWorkTime(workTime)

            assertThrows<IllegalArgumentException> {
                service?.addWorkTime(workTime)
            }
        }
    }
    /**
     * Funckija koja testira metodu addWorkTime, kada je prosledjeni parametar ispravan
     * @return [WorkTime] koji je dodat test prolazi
     */
    @Test
    fun addWorkTime_test_ispravno(){
        runBlocking {
            service?.addWorkTime(workTime)

            assertTrue(service?.getWorkTimeForDoctor("1")?.contains(workTime)==true)
        }
    }

    /**
     * Funkcija koja testira metodu deleteWorkingTime , kada je prosledjeni parametar null
     * @throws NullPointerException Ako je bacen izuzetak test prolazi
     */
    @Test
    fun deleteWorkingTime_test_null(){
        runBlocking {
            assertThrows<NullPointerException> { service?.deleteWorkingTime(null) }
        }
    }
    /**
     * Funkcija koja testira metodu deleteWorkingTime , kada je prosledjeni parametar prazan string
     * @throws IllegalArgumentException Ako je bacen izuzetak test prolazi
     */
    @Test
    fun deleteWorkingTime_test_prazanId(){
        runBlocking {
            assertThrows<IllegalArgumentException> { service?.deleteWorkingTime("") }
        }
    }
    /**
     * Funkcija koja testira metodu deleteWorkingTime , kada  prosledjeni parametar ne postoji u bazi
     * @return Ako je vracena false vrednost test prolazi
     */
    @Test
    fun deleteWorkingTime_test_NePostoji(){
        runBlocking {
            assertFalse(service?.deleteWorkingTime("4")==true)
        }
    }
    /**
     * Funkcija koja testira metodu deleteWorkingTime , kada  prosledjeni parametar  postoji u bazi
     * @return Ako je vracena true vrednost test prolazi
     */
    @Test
    fun deleteWorkingTime_uspesno(){
        runBlocking {
            service?.addWorkTime(workTime)
            assertTrue(service?.deleteWorkingTime("1")==true)
        }
    }
    /**
     * Funkcija koja testira metodu updateWorkingTime , kada je prosledjeni parametar null
     * @throws NullPointerException Ako je bacen izuzetak test prolazi
     */
    @Test
    fun updateWorkingTime_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                service?.updateWorkingTime(null)
            }
        }

    }
    /**
     * Funkcija koja testira metodu deleteWorkingTime , kada prosledjeni parametar ne postoji u bazi
     * @throws IllegalArgumentException Ako je bacen izuzetak test prolazi
     */
    @Test
    fun updateWorkingTime_nePostoji(){
        runBlocking {
           val workTime2= WorkTime("2", LocalTime.of(8,0), LocalTime.of(15,0),"1", DayInWeek.MONDAY)


            assertThrows<IllegalArgumentException> {
                service?.updateWorkingTime(workTime2)
            }
        }

    }
    /**
     * Funkcija koja testira metodu updateWorkingTime , kada je prosledjeni parametar ispravan i postoji u bazi
     * @return [WorkTime] Ako je vraceni objekat jednak prosledjenom, test prolazi
     */
    @Test
    fun updateWorkingTime_ispravno(){
        runBlocking {
            val workTime2= WorkTime("1", LocalTime.of(9,0), LocalTime.of(15,0),"1", DayInWeek.MONDAY)

            service?.addWorkTime(workTime)


            assertEquals(workTime2,service?.updateWorkingTime(workTime))
        }

    }



}