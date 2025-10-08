package com.example.repositoryTests

import com.example.database.DatabaseFactory
import com.example.domain.Doctor
import com.example.domain.Hospital
import com.example.domain.Patient
import com.example.domain.Role
import com.example.domain.Termin
import com.example.domain.TerminStatus
import com.example.domain.User
import com.example.repository.termin.TerminRepository
import com.example.repository.termin.TerminRepositoryImplementation
import com.example.response.BaseResponse
import com.example.response.ListResponse
import com.example.service.DoctorServiceInterface
import com.example.service.doctor.DoctorServiceImplementation
import com.example.service.hospital.HospitalServiceImplementation
import com.example.service.hospital.HospitalServiceInterface
import com.example.service.patient.PatientServiceImplementation
import com.example.service.patient.PatientServiceInterface
import com.example.service.termin.TerminServiceImplementation
import com.example.service.user.UserServiceImplementation
import com.example.service.user.UserServiceInterface
import io.ktor.test.dispatcher.testSuspend
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalTime
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


/**
 * Test klasa za [TerminRepository]
 * @author Lazar Jankovic
 * @see TerminRepository
 * @see Termin
 */
class TerminRepositoryTests {

    private lateinit var termin: Termin
    private lateinit var repository: TerminRepository
    private lateinit var hospital: Hospital
    private lateinit var hospitalService: HospitalServiceInterface
    private lateinit var user1: User
    private lateinit var user2: User
    private lateinit var userService: UserServiceInterface
    private lateinit var doctor: Doctor
    private lateinit var patient: Patient
    private lateinit var patientServiceInterface: PatientServiceInterface
    private lateinit var doctorServiceInterface: DoctorServiceInterface

    /**
     * Pre svakog testa se povezujemo sa bazom, brisemo podatke iz tabela kako ne bi dolazilo do dupliranja,
     * nakon toga se inicijalizuju objekti i servisi koji su nam potrebni radi kreiranja
     * termina
     */
    @BeforeEach
    fun setUp()
    {
        DatabaseFactory.init()
        DatabaseFactory.clearTable("termins")
        DatabaseFactory.clearTable("users")
        DatabaseFactory.clearTable("hospital")
        DatabaseFactory.clearTable("patient")
        DatabaseFactory.clearTable("doctor")





        hospitalService= HospitalServiceImplementation()
        patientServiceInterface= PatientServiceImplementation()
        doctorServiceInterface= DoctorServiceImplementation()
        userService= UserServiceImplementation()
        repository= TerminRepositoryImplementation(
            service = TerminServiceImplementation(),
            doctorService = doctorServiceInterface,
            patientService = patientServiceInterface,
            hospitalService = hospitalService
        )
        user1= User(
            email = "doctor@gmail.com",
            password = "Password123.",
            role = Role.ROLE_DOCTOR
        )
        user2= User(
            email = "patient@gmail.com",
            password = "Password123.",
            role = Role.ROLE_PATIENT
        )
        runBlocking {
            user1=userService.addUser(user1)!!
            user2=userService.addUser(user2)!!
        }
        hospital= Hospital(
            name = "Opsta bolnica Cacak",
            city = "Cacak",
            address = "Milosa Obrenovica 2"
        )
        runBlocking {
            hospital=hospitalService.addHospital(hospital)!!
        }
        patient= Patient(

            userId = user2.getId(),
            fullName = "Pera Peric",
            hospitalId = hospital.getId()!!,
            jmbg = "1007002790023"
        )
        doctor= Doctor(

            userId = user1.getId(),
            fullName = "Mika Mikic",
            specialization = "Neurolog",
            maxPatients = 30,
            currentPatients = 9,
            hospitalId = hospital.getId()!!,
            isGeneral = false
        )
        runBlocking {
            patient=patientServiceInterface.addPatient(patient)!!
            doctor=doctorServiceInterface.addDoctor(doctor)!!
        }
        termin= Termin(

            doctorId =doctor.getId()!!,
            patientId = patient.getId()!!,
            date = LocalDate.of(2026,11,25),
            startTime = LocalTime.of(15,0),
            hospitalId = hospital.getId()!!,
            status = TerminStatus.ON_HOLD
        )

    }
    @Test
    fun addTermin_testNull()=runBlocking {
        val result=repository.addTermin(null )
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Termin koji zelite da dodate ne moze biti null",result.message)
    }
    @Test
    fun addTermin_doctorNotExist()=runBlocking {
        val result=repository.addTermin(termin.copy(doctorId = "9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9"))
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Taj lekar ne postoji",result.message)

    }
    @Test
    fun addTermin_patientNotExist()=runBlocking {
        val result=repository.addTermin(termin.copy(patientId = "9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9"))
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Pacijent ne postoji",result.message)

    }
    @Test
    fun addTermin_hospitalNotExist()=runBlocking {
        val result=repository.addTermin(termin.copy(hospitalId = "9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9"))
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Bolnica ne postoji",result.message)

    }
    @Test
    fun addTermin_alreadyExist()=runBlocking {
        termin=(repository.addTermin(termin) as BaseResponse.SuccessResponse).data!!
        val result=repository.addTermin(termin)
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Termin vec postoji",result.message)

    }
    @Test
    fun addTermin_testUspesno()=runBlocking {
        val result=repository.addTermin(termin)
        assertTrue(result is BaseResponse.SuccessResponse)

    }
    @Test
    fun editTermin_null()=runBlocking {
        val result=repository.editTermin(null)
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Termin koji zelite da izmenite ne moze biti null",result.message)
    }
    @Test
    fun editTermin_terminNePostoji()=runBlocking {
        termin=termin.copy(id = "9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9")
        val result=repository.editTermin(termin)
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Termin ne postoji",result.message)
    }
    @Test
    fun editTermin_uspesno()=runBlocking {
        termin=(repository.addTermin(termin) as BaseResponse.SuccessResponse).data!!
        val result=repository.editTermin(termin.copy(endTime = LocalTime.of(18,0), status = TerminStatus.SCHEDULDED))
        println(termin)
        assertTrue(result is BaseResponse.SuccessResponse)
        println(result.data)

        assertFalse(termin.equals(result.data))

    }
    @Test
    fun deleteTermin_nullTest()=runBlocking {
        val result=repository.deleteTermin(null)
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Id termina koji zelite da obrisete ne moze biti null",result.message)
    }
    @Test
    fun deleteTermin_notExistTest()=runBlocking {
        val result=repository.deleteTermin("9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9")
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Termin ne postoji",result.message)
    }
    @Test
    fun deleteTermin_uspesnoTest()=runBlocking {
        termin=(repository.addTermin(termin) as BaseResponse.SuccessResponse).data!!
        val result=repository.deleteTermin(termin.getId())
        assertTrue(result is BaseResponse.SuccessResponse)
        assertTrue(result.data!!)
    }
    @Test
    fun getTerminForId_testNull()=runBlocking {
        val result=repository.getTerminForId(null)
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Id termina koji trazite ne moze biti null",result.message)
    }
    @Test
    fun getTerminForId_testNotExist()=runBlocking {
        val result=repository.getTerminForId("9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9")
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Termin nije pronadjen",result.message)
    }
    @Test
    fun getTerminForId_testIspravno()=runBlocking {
        termin=(repository.addTermin(termin) as BaseResponse.SuccessResponse).data!!
        val result=repository.getTerminForId(termin.getId())
        assertTrue(result is BaseResponse.SuccessResponse)
        assertEquals(termin,result.data)
    }
    @Test
    fun getTerminForDoctor_testNull()=runBlocking {
        val result=repository.getTerminsForDoctor(null)
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Id lekara cije termine trazite ne moze biti null",result.message)
    }
    @Test
    fun getTerminForDoctor_testNotExist()=runBlocking {
        val result=repository.getTerminsForDoctor("9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9")
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Ovaj lekar nema termina",result.message)
    }
    @Test
    fun getTerminForDoctor_testIspravno()=runBlocking {
        termin=(repository.addTermin(termin) as BaseResponse.SuccessResponse).data!!
        val result=repository.getTerminsForDoctor(termin.getDoctorId())
        assertTrue(result is ListResponse.SuccessResponse)
        assertEquals(1,result.data?.size)
    }
    @Test
    fun getTerminForPatient_testNull()=runBlocking {
        val result=repository.getTerminsForPatient(null)
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Id pacijenta cije termine trazite ne moze biti null",result.message)
    }
    @Test
    fun getTerminForPatient_testNotExist()=runBlocking {
        val result=repository.getTerminsForPatient("9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9")
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Ovaj pacijent nema termina",result.message)
    }
    @Test
    fun getTerminForPatient_testIspravno()=runBlocking {
        termin=(repository.addTermin(termin) as BaseResponse.SuccessResponse).data!!
        val result=repository.getTerminsForPatient(termin.getPatientId())
        assertTrue(result is ListResponse.SuccessResponse)
        assertEquals(1,result.data?.size)
    }
    @Test
    fun getTerminForDoctorForDate_testNull()=runBlocking {
        val result=repository.getTerminsForDoctorForDate(null, LocalDate.of(2025,12,20))
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Id lekara cije termine trazite ne moze biti null",result.message)
    }
    @Test
    fun getTerminForDoctorForDate_testNotExist()=runBlocking {
        val result=repository.getTerminsForDoctorForDate("9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9",
            LocalDate.of(2025,12,12))
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Ovaj lekar nema termina",result.message)
    }
    @Test
    fun getTerminForDoctorForDate_testIspravno()=runBlocking {
        termin=(repository.addTermin(termin) as BaseResponse.SuccessResponse).data!!
        val result=repository.getTerminsForDoctorForDate(termin.getDoctorId(),termin.getDate())
        assertTrue(result is ListResponse.SuccessResponse)
        assertEquals(1,result.data?.size)
    }
    @Test
    fun getTerminForPatientForDate_testNull()=runBlocking {
        val result=repository.getTerminsForPatientForDate(null,LocalDate.of(2025,12,12))
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Id pacijenta cije termine trazite ne moze biti null",result.message)
    }
    @Test
    fun getTerminForPatientForDate_testNotExist()=runBlocking {
        val result=repository.getTerminsForPatientForDate("9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9",LocalDate.of(2025,12,12))
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Ovaj pacijent nema termina",result.message)
    }
    @Test
    fun getTerminForPatientForDate_testIspravno()=runBlocking {
        termin=(repository.addTermin(termin) as BaseResponse.SuccessResponse).data!!
        val result=repository.getTerminsForPatientForDate(termin.getPatientId(),termin.getDate())
        assertTrue(result is ListResponse.SuccessResponse)
        assertEquals(1,result.data?.size)
    }



}