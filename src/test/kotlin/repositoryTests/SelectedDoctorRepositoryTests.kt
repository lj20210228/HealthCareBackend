package com.example.repositoryTests

import com.example.database.DatabaseFactory
import com.example.domain.Doctor
import com.example.domain.Hospital
import com.example.domain.Patient
import com.example.domain.Role
import com.example.domain.SelectedDoctor
import com.example.domain.User
import com.example.repository.selectedDoctor.SelectedDoctorRepository
import com.example.repository.selectedDoctor.SelectedDoctorRepositoryImplementation
import com.example.response.BaseResponse
import com.example.response.ListResponse
import com.example.service.DoctorServiceInterface
import com.example.service.doctor.DoctorServiceImplementation
import com.example.service.hospital.HospitalServiceImplementation
import com.example.service.hospital.HospitalServiceInterface
import com.example.service.patient.PatientServiceImplementation
import com.example.service.patient.PatientServiceInterface
import com.example.service.selectedDoctor.SelectedDoctorServiceImplementation
import com.example.service.selectedDoctor.SelectedDoctorServiceInterface
import com.example.service.user.UserServiceImplementation
import com.example.service.user.UserServiceInterface
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.jvm.Throws
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Klasa koja testira [SelectedDoctorRepository]
 * @author Lazar Jankovic
 * @see SelectedDoctorRepository
 * @see SelectedDoctor
 * @see DoctorServiceInterface
 * @see HospitalServiceInterface
 * @see UserServiceInterface
 * @see PatientServiceInterface
 * @see User
 * @see Doctor
 * @see Patient
 */
class SelectedDoctorRepositoryTests {

    private lateinit var hospitalService: HospitalServiceInterface
    private lateinit var userService: UserServiceInterface
    private lateinit var doctorService: DoctorServiceInterface
    private lateinit var patientService: PatientServiceInterface
    private lateinit var user: User
    private lateinit var user2: User

    private lateinit var doctor: Doctor
    private lateinit var patient: Patient

    private lateinit var hospital: Hospital
    private lateinit var repository: SelectedDoctorRepository
    private lateinit var selectedDoctor: SelectedDoctor
    private lateinit var service: SelectedDoctorServiceInterface

    /**
     * Pre svakog testa se inicijalizuje baza podataka i brisu se svi podaci iz tabele selected_doctor,
     * inicijalizuju se servisi koji su nam potrebni, jer pre dodavanja [SelectedDoctor]
     * moramo dodati hospital gde su pacijenti, kao i usere, lekara i pacijenta kako bi imali spoljne kljuceve
     */
    @BeforeEach
    fun setUp(){
        DatabaseFactory.init()

        DatabaseFactory.clearTable("selected_doctor")
        hospitalService= HospitalServiceImplementation()
        userService= UserServiceImplementation()
        doctorService= DoctorServiceImplementation()
        patientService= PatientServiceImplementation()
        service= SelectedDoctorServiceImplementation(
            doctorService,patientService
        )
        repository= SelectedDoctorRepositoryImplementation(
            service = service,
        )


        hospital= Hospital(
            name = "Opsta bolnica Cacak",
            city = "Cacak",
            address = "Kneza Milosa 2"
        )
        hospitalService= HospitalServiceImplementation()
        runBlocking {
            hospital=hospitalService.addHospital(hospital)!!
        }
        user= User(
            email = "pera@peric.com",
            password = "Password123!",
            role = Role.ROLE_DOCTOR,
        )
        user2= User(
            email = "mika@peric.com",
            password = "Password123!",
            role = Role.ROLE_DOCTOR,
        )
        runBlocking {
            user=userService.addUser(user)!!
            user2=userService.addUser(user2)!!

        }



        doctor= Doctor(
            userId = user.getId(),
            fullName = "Pera Peric",
            specialization = "Ortoped",
            maxPatients = 30,
            currentPatients = 30,
            hospitalId = hospital.getId()!!,
            isGeneral = true
        )

        patient= Patient(
            userId = user2.getId(),
            fullName = "Petar Petrovic",
            hospitalId = hospital.getId()!!,
            jmbg = "1007002790023"
        )
        runBlocking {
            doctor=doctorService.addDoctor(doctor)!!

        }
        runBlocking {
            patient=patientService.addPatient(patient)!!

        }
        selectedDoctor= SelectedDoctor(
            patientId = patient.getId()!!,
            doctorId = doctor.getId()!!
        )


    }

    /**
     * Dodavanje izabranog lekara kad je prosledjeni argument null
     */
    @Test
    fun addSelectedDoctorForPatient_testNull()=runBlocking {
        val result=repository.addSelectedDoctorForPatient(null)
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Niste uneli ispravne podatke za izabranog lekara",result.message)
    }

    /**
     * Dodavanje izabranog lekara kada vec postoji za pacijenta
     */
    @Test
    fun addSelectedDoctorForPatient_TestAlreadyExist()=runBlocking {
        service.addSelectedDoctorForPatient(selectedDoctor)
        val result=repository.addSelectedDoctorForPatient(selectedDoctor)
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Izabrani lekar za ovog pacijenta vec postoji.",result.message)
    }
    /**
     * Dodavanje izabranog lekara ispravno
     */
    @Test
    fun addSelectedDoctorForPatient_ispravnoTest()=runBlocking {
        val result=repository.addSelectedDoctorForPatient(selectedDoctor)
        assertTrue(result is BaseResponse.SuccessResponse)
        assertEquals(selectedDoctor,result.data)
    }
    /**
     * Trazenje svih izabranih lekara za pacijenta kada nema izbranih lekara
     */
    @Test
    fun getSelectedDoctorForPatients_testNemaIzabranih()=runBlocking {
        val result=repository.getSelectedDoctorsForPatients("9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9")
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Ovaj pacijent nema ni jednog izabranog lekara",result.message)
    }
    /**
     * Trazenje svih izabranih lekara za pacijenta kada ima izbranih lekara
     */
    @Test
    fun getSelectedDoctorsForPatients_testIspravno()=runBlocking {
        service.addSelectedDoctorForPatient(selectedDoctor)
        val result=repository.getSelectedDoctorsForPatients(patient.getId())
        assertTrue(result is ListResponse.SuccessResponse)
        assertEquals(1,result.data?.size)
    }
    /**
     * Trazenje svih pacijenata za izabranog lekara kada nema pacijenata
     */
    @Test
    fun getPatientsForSelectedDoctor_test_null()=runBlocking {
        val result=repository.getPatientsForSelectedDoctor("9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9")
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Ovaj lekar nije izabran nikome",result.message)
    }
    /**
     * Trazenje svih pacijenata za izabranog lekara kada ima pacijenata
     */
    @Test
    fun getPatientsForSelectedDoctors_testIspravno()=runBlocking {
        service.addSelectedDoctorForPatient(selectedDoctor)
        val result=repository.getPatientsForSelectedDoctor(doctor.getId())
        assertTrue(result is ListResponse.SuccessResponse)
        assertEquals(1,result.data?.size)
    }



}