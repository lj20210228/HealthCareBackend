package com.example.repositoryTests

import com.example.database.DatabaseFactory
import com.example.domain.Doctor
import com.example.domain.Hospital
import com.example.domain.Role
import com.example.domain.User
import com.example.repository.doctor.DoctorRepository
import com.example.repository.doctor.DoctorRepositoryImplementation
import com.example.response.BaseResponse
import com.example.response.ListResponse
import com.example.service.DoctorServiceInterface
import com.example.service.doctor.DoctorServiceImplementation
import com.example.service.hospital.HospitalServiceImplementation
import com.example.service.hospital.HospitalServiceInterface
import com.example.service.user.UserServiceImplementation
import com.example.service.user.UserServiceInterface
import com.fasterxml.jackson.databind.ser.Serializers
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DoctorRepositoryTests {

    private lateinit var service: DoctorServiceInterface
    private lateinit var userService: UserServiceInterface
    private lateinit var user: User
    private lateinit var hospitalService: HospitalServiceInterface
    private lateinit var hospital: Hospital
    private lateinit var hospitalAdded: Hospital
    private lateinit var repository: DoctorRepository
    private lateinit var doctor: Doctor
    private lateinit var doctor2: Doctor
    private lateinit var doctor3: Doctor


    @BeforeEach
    fun setUp(){
        service= DoctorServiceImplementation()
        repository= DoctorRepositoryImplementation(service, HospitalServiceImplementation())
        userService= UserServiceImplementation()
        DatabaseFactory.init()
        DatabaseFactory.clearTable("doctor")


        hospital= Hospital(
            name = "Opsta bolnica Cacak",
            city = "Cacak",
            address = "Kneza Milosa 2"
        )
        hospitalService= HospitalServiceImplementation()
        runBlocking {
            hospitalAdded=hospitalService.addHospital(hospital)!!
        }
        user= User(

            email = "pera@peric.com",
            password = "Password123!",
            role = Role.ROLE_DOCTOR,

        )
        var userReturned: User
        runBlocking {
            userReturned=userService.addUser(user)!!
        }
        doctor= Doctor(
            userId = userReturned.getId(),
            fullName = "Pera Peric",
            specialization = "Ortoped",
            maxPatients = 30,
            currentPatients = 30,
            hospitalId = hospitalAdded.getId()!!,
            isGeneral = true
        )
        doctor2= Doctor(
            userId = userReturned.getId(),
            fullName = "Pera Peric",
            specialization = "Neurolog",
            maxPatients = 30,
            currentPatients = 30,
            hospitalId = hospitalAdded.getId()!!,
            isGeneral = false
        )
        doctor3= Doctor(
            userId = userReturned.getId(),
            fullName = "Pera Peric",
            specialization = "Neurolog",
            maxPatients = 30,
            currentPatients = 10,
            hospitalId = hospitalAdded.getId()!!,
            isGeneral = true
        )



    }
    @AfterEach
    fun tearDown(){
        DatabaseFactory.clearTable("doctor")
    }

    @Test
    fun addDoctor_nullTest()= runBlocking {
        val doctor=repository.addDoctor(null)
        assertTrue(doctor is BaseResponse.ErrorResponse)
        assertEquals("Niste uneli ispravne podatke o lekaru", doctor.message)
    }
    @Test
    fun addDoctor_vecPostoji()= runBlocking {
        service.addDoctor(doctor)
        val doctor=repository.addDoctor(doctor)
        assertTrue(doctor is BaseResponse.ErrorResponse)
        assertEquals("Lekar vec postoji", doctor.message)
    }
    @Test
    fun addDoctor_uspesno()= runBlocking {

        val doctorAdded=repository.addDoctor(doctor)
        assertTrue(doctorAdded is BaseResponse.SuccessResponse)
        assertEquals(doctor, doctorAdded.data)
    }
    @Test
    fun getDoctorForId_testNull()=runBlocking {
        val test=repository.getDoctorForId(null)
        assertTrue(test is BaseResponse.ErrorResponse)
        assertEquals("Niste uneli ispravne podatke o lekaru",test.message)
    }
    @Test
    fun getDoctorForId_testNePostoji()=runBlocking {
        val test=repository.getDoctorForId("9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9")
        assertTrue(test is BaseResponse.ErrorResponse)
        assertEquals("Lekar sa tim id ne postoji",test.message)
    }
    @Test
    fun getDoctorForId_testPostoji()=runBlocking {
        val doctorAdded=service.addDoctor(doctor)
        val test=repository.getDoctorForId(doctorAdded?.getId())
        assertTrue(test is BaseResponse.SuccessResponse)
        assertEquals(doctor,test.data)
    }
    @Test
    fun getAllGeneralDoctorsInHospitalWithoutMaxPatients_Ispravnotest()=runBlocking {
        service.addDoctor(doctor)
        service.addDoctor(doctor2)
        service.addDoctor(doctor3)

        val doctors=repository.getAllGeneralDoctorsInHospitalWithoutMaxPatients(hospitalAdded.getId())
        println(doctors)

        assertTrue(doctors is ListResponse.SuccessResponse)
        assertEquals(1,doctors.data?.size)
    }
    @Test
    fun getAllGeneralDoctorsInHospitalWithoutMaxPatients_Neispravnotest()=runBlocking {
        service.addDoctor(doctor)
        service.addDoctor(doctor2)

        val doctors=repository.getAllGeneralDoctorsInHospitalWithoutMaxPatients(hospitalAdded.getId())
        println(doctors)
        assertTrue(doctors is ListResponse.ErrorResponse)
        assertEquals("Nema lekara opste prakse koji mogu da budu izabrani u ovoj bolnici",doctors.message)
    }
    @Test
    fun getAllGeneralDoctorsInHospital_Ispravnotest()=runBlocking {
        service.addDoctor(doctor)
        service.addDoctor(doctor2)
        service.addDoctor(doctor3)

        val doctors=repository.getAllGeneralDoctorsInHospital(hospitalAdded.getId())

        assertTrue(doctors is ListResponse.SuccessResponse)
        assertEquals(2,doctors.data?.size)
    }
    @Test
    fun getAllGeneralDoctorsInHospital_Neispravnotest()=runBlocking {
        service.addDoctor(doctor2)

        val doctors=repository.getAllGeneralDoctorsInHospital(hospitalAdded.getId())
        println(doctors)
        assertTrue(doctors is ListResponse.ErrorResponse)
        assertEquals("Nema lekara opste prakse u ovoj bolnci",doctors.message)
    }
    @Test
    fun getAllDoctorsInHospitalForSpecialization_Ispravnotest()=runBlocking {
        service.addDoctor(doctor)
        service.addDoctor(doctor2)
        service.addDoctor(doctor3)

        val doctors=repository.getAllDoctorInHospitalForSpecialization(hospitalAdded?.getId(),"Ortoped")
        println(doctors)

        assertTrue(doctors is ListResponse.SuccessResponse)
        assertEquals(1,doctors.data?.size)
    }
    @Test
    fun getAllDoctorsInHospitalForSpecialization_Neispravnotest()=runBlocking {
        service.addDoctor(doctor2)
        service.addDoctor(doctor)
        service.addDoctor(doctor3)

        val doctors=repository.getAllDoctorInHospitalForSpecialization(hospitalAdded.getId(),"Dermatolog")
        println(doctors)
        assertTrue(doctors is ListResponse.ErrorResponse)
        assertEquals("Nema lekara ove specijalizacije u ovoj bolnci",doctors.message)
    }
    @Test
    fun getAllDoctorsInHospitalForSpecializationWithoutMaxPatients_Ispravnotest()=runBlocking {
        service.addDoctor(doctor)
        service.addDoctor(doctor2)
        service.addDoctor(doctor3)

        val doctors=repository.getAllDoctorsInHospitalWithoutMaxPatientsForSpecialization(hospitalAdded?.getId(),"Neurolog")
        println(doctors)

        assertTrue(doctors is ListResponse.SuccessResponse)
        assertEquals(1,doctors.data?.size)
    }
    @Test
    fun getAllDoctorsInHospitalForSpecializationWithoutMaxPatients_Neispravnotest()=runBlocking {
        service.addDoctor(doctor2)
        service.addDoctor(doctor)

        val doctors=repository.getAllDoctorsInHospitalWithoutMaxPatientsForSpecialization(hospitalAdded.getId(),"Neurolog")
        println(doctors)
        assertTrue(doctors is ListResponse.ErrorResponse)
        assertEquals("Nema lekara ove specijalizacije koji mogu da budu izabrani u ovoj bolnci",doctors.message)
    }

    @Test
    fun editCurrentPatients_ispravno()=runBlocking {
        val added=service.addDoctor(doctor3)
        val result=repository.editCurrentPatients(added?.getId())
        assertTrue(result is BaseResponse.SuccessResponse)
        assertTrue(result.data==true)

    }
    @Test
    fun editCurrentPatients_imaMaksPacijenata()=runBlocking {
        val added=service.addDoctor(doctor)
        val result=repository.editCurrentPatients(added?.getId())
        println(result)
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Nisu uspesno azuzirani podaci o trenutnom broju pacijenata",result.message)

    }

}