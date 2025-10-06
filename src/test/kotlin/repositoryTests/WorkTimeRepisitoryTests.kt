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

class WorkTimeRepisitoryTests {
    private lateinit var hospitalService: HospitalServiceInterface
    private lateinit var hospital: Hospital
    private lateinit var userService: UserServiceInterface
    private lateinit var user: User
    private lateinit var doctorService: DoctorServiceInterface
    private lateinit var doctor: Doctor
    private lateinit var repository: WorkTimeRepository
    private lateinit var workTime: WorkTime

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
    @Test
    fun getWorkTimeForDoctorId_nullTest()=runBlocking {
        val result=repository.getWorkTimeForDoctor(null)
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Niste prosledili podatke o lekaru",result.message)
    }
    @Test
    fun getWorkTimeForDoctorId_LekarNePostoji()=runBlocking {
        val result=repository.getWorkTimeForDoctor("9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9")
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Lekar cije radno vreme trazite ne postoji",result.message)
    }
    @Test
    fun getWorkTimeForDoctorId_radnoVremeNePostoji()=runBlocking {
        val result=repository.getWorkTimeForDoctor(doctor.getId())
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Lekar nije oznacio svoje radno vreme",result.message)
    }
    @Test
    fun getWorkTimeForDoctorId_radnoVremePostoji()=runBlocking {
        repository.addWorkTime(workTime)
        val result=repository.getWorkTimeForDoctor(doctor.getId())
        assertTrue(result is ListResponse.SuccessResponse)
        assertEquals(1,result.data?.size)
    }


}