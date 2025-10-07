package com.example.repositoryTests

import com.example.UserService
import com.example.database.DatabaseFactory
import com.example.domain.DayInWeek
import com.example.domain.Doctor
import com.example.domain.Hospital
import com.example.domain.Role
import com.example.domain.User
import com.example.domain.WorkTime
import com.example.repository.doctor.DoctorRepositoryImplementation
import com.example.repository.workTime.WorkTimeRepository
import com.example.repository.workTime.WorkTimeRepositoryImplementation
import com.example.response.BaseResponse
import com.example.response.ListResponse
import com.example.service.DoctorServiceInterface
import com.example.service.doctor.DoctorServiceImplementation
import com.example.service.hospital.HospitalServiceImplementation
import com.example.service.hospital.HospitalServiceInterface
import com.example.service.user.UserServiceImplementation
import com.example.service.user.UserServiceInterface
import com.example.service.workTime.WorkTimeServiceImplementation
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalTime
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Testovi za klasu [WorkTimeRepository]
 * @author Lazar Jankovic
 * @see WorkTimeRepository
 * @see WorkTime
 * @see HospitalServiceInterface
 * @see UserServiceInterface
 * @see DoctorServiceInterface
 */
class WorkTimeRepisitoryTests {
    private lateinit var hospitalService: HospitalServiceInterface
    private lateinit var hospital: Hospital
    private lateinit var userService: UserServiceInterface
    private lateinit var user: User
    private lateinit var doctorService: DoctorServiceInterface
    private lateinit var doctor: Doctor
    private lateinit var repository: WorkTimeRepository
    private lateinit var workTime: WorkTime

    /**
     * Pre svakog testa se povezuje sa bazom i brisu se svi podaci iz tabele work_time,
     * takodje se prave i u bazu dodaju objekti [Hospital],[User] i [Doctor], jer su nam oni neophodni
     * zbog radnog vremena
     */
    @BeforeEach
    fun  setUp(){
        DatabaseFactory.init()
        DatabaseFactory.clearTable("work_time")
        hospitalService= HospitalServiceImplementation()
        doctorService= DoctorServiceImplementation()
        repository= WorkTimeRepositoryImplementation(
            service = WorkTimeServiceImplementation(),
            doctorService = doctorService
        )
        hospital= Hospital(

            name = "Bolnica Cacak",
            city = "Cacak",
            address = "Milosa obrenovica 3"
        )
        runBlocking {
          hospital=  hospitalService.addHospital(hospital)!!

        }
        userService= UserServiceImplementation()
        user= User(

            email = "pera@peric.com",
            password = "Password123.",
            role = Role.ROLE_DOCTOR
        )
        runBlocking {
            user=userService.addUser(user)!!
        }
        doctor= Doctor(

            userId = user.getId(),
            fullName = "Pera Peric",
            specialization = "Dermatolog",
            maxPatients = 20,
            currentPatients = 2,
            hospitalId = hospital.getId()!!,
            isGeneral = false
        )
        runBlocking {
            doctor=doctorService.addDoctor(doctor)!!
        }
        workTime= WorkTime(

            startTime = LocalTime.of(8,0),
            endTime = LocalTime.of(15,0),
            doctorId = doctor.getId()!!,
            dayIn = DayInWeek.MONDAY
        )

    }

    /**
     * Test za pretragu radnog vremena lekara kada je prosledjen null argument
     */
    @Test
    fun getWorkTimeForDoctorId_nullTest()=runBlocking {
        val result=repository.getWorkTimeForDoctor(null)
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Niste prosledili podatke o lekaru",result.message)
    }
    /**
     * Test za pretragu radnog vremena lekara kada je prosledjen id lekara koji ne postoji
     */
    @Test
    fun getWorkTimeForDoctorId_LekarNePostoji()=runBlocking {
        val result=repository.getWorkTimeForDoctor("9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9")
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Lekar cije radno vreme trazite ne postoji",result.message)
    }
    /**
     * Test za pretragu radnog vremena lekara kada je prosledjen id lekara cije radno vreme ne postoji
     */
    @Test
    fun getWorkTimeForDoctorId_radnoVremeNePostoji()=runBlocking {
        val result=repository.getWorkTimeForDoctor(doctor.getId())
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Lekar nije oznacio svoje radno vreme",result.message)
    }
    /**
     * Test za pretragu radnog vremena lekara kada je prosledjen id lekara cije radno vreme postoji
     */
    @Test
    fun getWorkTimeForDoctorId_radnoVremePostoji()=runBlocking {
        repository.addWorkTime(workTime)
        val result=repository.getWorkTimeForDoctor(doctor.getId())
        assertTrue(result is ListResponse.SuccessResponse)
        assertEquals(1,result.data?.size)
    }

    /**
     * Test za dodavanje radnog vremena kada je prosledjen null argument
     */
    @Test
    fun addWorkTime_testNull()=runBlocking {
        val result=repository.addWorkTime(null)
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Niste prosledili podatke o radnom vremenu",result.message)
    }

    /**
     * Test za dodavanje radnog vremena kada je prosledjeno radno vreme sa id lekara koji ne postoji
     */
    @Test
    fun addWorkTime_testLekarNePostoji()=runBlocking {
        val workTime=workTime.copy(doctorId = "9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9")
        val result=repository.addWorkTime(workTime)
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Lekar ne postoji",result.message)
    }

    /**
     * Test za dodavanje radnog vremena kada je prosledjeno radno vreme koje vec postoji
     */
    @Test
    fun addWorkTime_testVecPostoji()=runBlocking {
        repository.addWorkTime(workTime)
        val result=repository.addWorkTime(workTime)
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Radno vreme vec postoji",result.message)
    }
    /**
     * Test za dodavanje radnog vremena kada je prosledjeno ispravno radno vreme
     */
    @Test
    fun addWorkTime_testUspesno()=runBlocking {
        val result=repository.addWorkTime(workTime)
        assertTrue(result is BaseResponse.SuccessResponse)
        assertEquals(workTime,result.data)
    }

    /**
     * Test za brisanje radnog vremena kada je prosledjeni argument null
     */
    @Test
    fun deleteWorkingTime_testNull()=runBlocking {
        val result=repository.deleteWorkingTime(null)
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Niste prosledili podatke za brisanje radnog vremena",result.message)
    }
    /**
     * Test za brisanje radnog vremena kada je prosledjeni argument id lekara koji ne postoji
     */
    @Test
    fun deleteWorkTime_testNePostoji()=runBlocking {
        val result=repository.deleteWorkingTime("9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9")
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Ne mozete da obrisete radno vreme koje ne postoji",result.message)

    }
    /**
     * Test za brisanje radnog vremena kada je prosledjeni argument id lekara koji postoji
     */
    @Test
    fun deleteWorkingTime_testUspesno()=runBlocking {
        val added=repository.addWorkTime(workTime)
        val result=repository.deleteWorkingTime((added as BaseResponse.SuccessResponse).data?.getId())
        assertTrue(result is BaseResponse.SuccessResponse)
        assertEquals(true,result.data)
    }

    /**
     * Test za azuriranje radnog vremena kad je prosledjeni argument null
     */
    @Test
    fun updateWorkingTime_testNull()=runBlocking {
        val result=repository.updateWorkingTime(null)
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Niste prosledili ispravne podatke za azuriranje",result.message)
    }
    /**
     * Test za azuriranje radnog vremena kad je prosledjeni argument radno vreme koje ne postoji
     */
    @Test
    fun updateWorkTime_testNePostoji()=runBlocking {
        val result=repository.updateWorkingTime(workTime.copy(id = "9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9"))
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Radno vreme koje pokusavate da azurirate ne postoji",result.message)

    }

    /**
     * Test za azuriranje radnog vremena kada je azuriranje uspesno
     */
    @Test
    fun updateWorkingTime_uspesno()=runBlocking {
        workTime=(repository.addWorkTime(workTime) as BaseResponse.SuccessResponse).data!!
        val result=repository.updateWorkingTime(workTime.copy(startTime = LocalTime.of(9,0)))
        assertTrue(result is BaseResponse.SuccessResponse)
        assertEquals(workTime.copy(startTime = LocalTime.of(9,0)),result.data)
    }




}