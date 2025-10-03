package com.example.repositoryTests

import com.example.database.DatabaseFactory
import com.example.domain.Hospital
import com.example.domain.Patient
import com.example.domain.Role
import com.example.domain.User
import com.example.repository.patient.PatientRepository
import com.example.repository.patient.PatientRepositoryImplementation
import com.example.response.BaseResponse
import com.example.response.ListResponse
import com.example.service.hospital.HospitalServiceImplementation
import com.example.service.hospital.HospitalServiceInterface
import com.example.service.patient.PatientServiceImplementation
import com.example.service.patient.PatientServiceInterface
import com.example.service.user.UserServiceImplementation
import com.example.service.user.UserServiceInterface
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Testovi za [PatientRepository]
 * @see PatientRepository
 * @author Lazar Jankovic
 * @see Patient
 * @see PatientServiceInterface
 * @see HospitalServiceInterface
 * @see UserServiceInterface
 */
class PatientRepositoryTests {
    private lateinit var service: PatientServiceInterface
    private lateinit var userService: UserServiceInterface
    private lateinit var user: User
    private lateinit var hospitalService: HospitalServiceInterface
    private lateinit var hospital: Hospital
    private lateinit var hospitalAdded: Hospital
    private lateinit var repository: PatientRepository
    private lateinit var patient: Patient

    /**
     * Pre svakog testa se inicijalizuju servisi koji ce nam biti potrebni,
     * dodaju se user i bolnica kako bismo imali njihove id pri dodavanju
     * pacijenta, kao i inicijalizacija baze podataka i praznjenje tabele doctor kako
     * ne bi dolazilo do konflikta
     */
    @BeforeEach
    fun setUp(){

        service= PatientServiceImplementation()

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
        repository= PatientRepositoryImplementation(
            service,
            hospitalService
        )
        user= User(

            email = "pera@peric.com",
            password = "Password123!",
            role = Role.ROLE_DOCTOR,

            )
        var userReturned: User
        runBlocking {
            userReturned=userService.addUser(user)!!
        }
        patient= Patient(

            userId = userReturned.getId(),
            fullName = "Mika Mikic",
            hospitalId = hospitalAdded.getId()!!,
            jmbg = "1007002790023"
        )

    }

    /**
     * Test za dodavanje pacijenta kada je prosledjena null vrednost
     */
    @Test
    fun addPatient_null()=runBlocking{
        val pat=repository.addPatient(null)
        assertTrue(pat is BaseResponse.ErrorResponse)
        assertEquals("Pacijent koji se dodaje mora imati vrenost",pat.message)

    }
    /**
     * Test za dodavanje pacijenta kada je prosledjena ispravna vrednost
     */
    @Test
    fun addPatient_Uspesno()=runBlocking{
        val pat=repository.addPatient(patient)
        assertTrue(pat is BaseResponse.SuccessResponse)
        assertEquals(patient,pat.data)

    }
    /**
     * Test pronalak pacijenta po id kada je prosledjena null vrednost
     */
    @Test
    fun getPatientById_null()=runBlocking {
        val pat=repository.getPatientById(null)
        assertTrue(pat is BaseResponse.ErrorResponse)
        assertEquals("Nisu uneti ispravni podaci za pronalazenje pacijenta",pat.message)
    }
    /**
     * Test pronalak pacijenta po id kada pacijent nije pronadjen
     */
    @Test
    fun getPatientById_NijePronadjen()=runBlocking {
        val pat=repository.getPatientById("9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9")
        assertTrue(pat is BaseResponse.ErrorResponse)
        assertEquals("Pacijent nije pronadjen",pat.message)
    }
    /**
     * Test pronalak pacijenta po id kada je pacijent uspesno pronadjen
     */
    @Test
    fun getPatientById_Pronadjen()=runBlocking {
        val added=service.addPatient(patient)
        val pat=repository.getPatientById(added?.getId())
        assertTrue(pat is BaseResponse.SuccessResponse)
        assertEquals(patient,pat.data)
    }
    /**
     * Test pronalak pacijenta po jmbg kada je prosledjena null vrednost
     */
    @Test
    fun getPatientByJmbg_null()=runBlocking {
        val pat=repository.getPatientByJmbg(null)
        assertTrue(pat is BaseResponse.ErrorResponse)
        assertEquals("Nisu uneti ispravni podaci za pronalazenje pacijenta",pat.message)
    }
    /**
     * Test pronalak pacijenta po jmbg kada pacijent nije pronadjen
     */
    @Test
    fun getPatientByJmbg_NijePronadjen()=runBlocking {
        val pat=repository.getPatientByJmbg("1100002780034")
        assertTrue(pat is BaseResponse.ErrorResponse)
        assertEquals("Pacijent nije pronadjen",pat.message)
    }
    /**
     * Test pronalak pacijenta po jmbg kada je pacijent uspesno pronadjen
     */
    @Test
    fun getPatientByJmbg_Pronadjen()=runBlocking {
        val added=service.addPatient(patient)
        val pat=repository.getPatientByJmbg(added?.getJmbg())
        assertTrue(pat is BaseResponse.SuccessResponse)
        assertEquals(patient,pat.data)
    }

    /**
     * Test za pronalazak svih pacijenata jedne bolnice kada je prosledjena null vrednost
     */
    @Test
    fun getAllPatientsInHospital_null()=runBlocking {
        val pat=repository.getAllPatientInHospital(null)
        assertTrue(pat is ListResponse.ErrorResponse)
        assertEquals("Prosledili ste pogresne podatke o bolnici",pat.message)
    }
    /**
     * Test za pronalazak svih pacijenata jedne bolnice kada bolnica ne postoji
     */
    @Test
    fun getAllPatientsInHospital_bolnicaNePostoji()=runBlocking {
        val pat=repository.getAllPatientInHospital("9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9")
        assertTrue(pat is ListResponse.ErrorResponse)
        assertEquals("Bolnica koju vi trazite ne postoji",pat.message)
    }
    /**
     * Test za pronalazak svih pacijenata jedne bolnice kada bolnica nema pacijente
     */
    @Test
    fun getAllPatientsInHospital_bolnicaNemaPacijente()=runBlocking {
        val pat=repository.getAllPatientInHospital(hospitalAdded.getId())
        assertTrue(pat is ListResponse.ErrorResponse)
        assertEquals("Bolnica nema pacijente",pat.message)
    }
    /**
     * Test za pronalazak svih pacijenata jedne bolnice kada bolnica ima pacijente
     */
    @Test
    fun getAllPatientsInHosptial_test_Pronadjen()=runBlocking {
        service.addPatient(patient)
        val pat=repository.getAllPatientInHospital(hospitalAdded.getId())
        assertTrue(pat is ListResponse.SuccessResponse)
        assertEquals(1,pat.data?.size)
    }
    /**
     * Test za menjanje pacijenta kada je prosledjena null vrednost
     */
    @Test
    fun editPatient_testNull()=runBlocking {
        val updated=repository.editPatient(null)
        assertTrue(updated is BaseResponse.ErrorResponse)
        assertEquals("Ne mozete izmeniti pacijenta koji nema vrednost",updated.message)
    }
    /**
     * Test za menjanje pacijenta kada je prosledjena ispravna vrednost, ali pacijent nije izmenjen
     */
    @Test
    fun editPatient_testNijeIzmenjen()=runBlocking {
        val edited=patient.copy("9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9")
        val editedResponse=repository.editPatient(edited)
        assertTrue(editedResponse is BaseResponse.ErrorResponse)
        assertEquals("Pacijent nije uspesno izmenjen",editedResponse.message)

    }

    /**
     * Test za menjanje pacijenta kada je prosledjena ispravna vrednost
     */
    @Test
    fun editPatient_testIspravno()=runBlocking {
        val added= service.addPatient(patient)
        val editedPatient=added?.copy(fullName = "Pera Mikic")
        val edited=repository.editPatient(editedPatient)
        assertTrue(edited is BaseResponse.SuccessResponse)
        assertEquals(editedPatient,edited.data)
    }

    /**
     * Test za brisanje pacijenta kada je prosledjena null vrednost
     */
    @Test
    fun deletePatient_nullTest()=runBlocking {
        val delete=repository.deletePatient(null)
        assertTrue(delete is BaseResponse.ErrorResponse)
        assertEquals("Niste prosledili parametre za pacijenta kog zelite da obrisete",delete.message)
    }
    /**
     * Test za brisanje pacijenta kada pacijent nije uspesno obrisan
     */
    @Test
    fun deletePatient_NijeObrisanTest()=runBlocking {
        val delete=repository.deletePatient("9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9")
        assertTrue(delete is BaseResponse.ErrorResponse)
        assertEquals("Pacijent nije uspesno obrisan",delete.message)
    }
    /**
     * Test za brisanje pacijenta kada je pacijent uspesno obrisan
     */
    @Test
    fun deletePatient_ObrisanTest()=runBlocking {
        val added=service.addPatient(patient)
        val delete=repository.deletePatient(added?.getId())
        assertTrue(delete is BaseResponse.SuccessResponse)
        assertEquals("Pacijent uspesno obrisan",delete.message)
    }


}