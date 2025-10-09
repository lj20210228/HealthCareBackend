package com.example.repositoryTests

import com.example.database.DatabaseFactory
import com.example.domain.Chat
import com.example.domain.Doctor
import com.example.domain.Hospital
import com.example.domain.Patient
import com.example.domain.Role
import com.example.domain.User
import com.example.repository.chat.ChatRepository
import com.example.repository.chat.ChatRepositoryImplementation
import com.example.response.BaseResponse
import com.example.response.ListResponse
import com.example.service.DoctorServiceInterface
import com.example.service.chat.ChatServiceImplementation
import com.example.service.doctor.DoctorServiceImplementation
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
 * Testovi za repozitorijum [ChatRepository]
 * @author Lazar Jankovic
 * @see ChatRepository
 * @see UserServiceInterface
 * @see DoctorServiceInterface
 * @see PatientServiceInterface
 *@see HospitalServiceInterface
 */
class ChatRepositoryTests {

    private lateinit var user1: User
    private lateinit var user2: User
    private lateinit var doctor: Doctor
    private lateinit var patient: Patient
    private lateinit var hospital: Hospital
    private lateinit var userService: UserServiceInterface
    private lateinit var hospitalServiceInterface: HospitalServiceInterface
    private lateinit var patientService: PatientServiceInterface
    private lateinit var doctorService: DoctorServiceInterface
    private lateinit var chat: Chat
    private lateinit var repository: ChatRepository

    /**
     * Pre pocetka svakog testa brisu se kolone iz baze kako ne bi dolazilo do preklapanja podataka i gresaka
     * , inicijalizuju se svi objekti koji su nam potrebni za testove, zbog spoljnih kljuceva i oni se dodaju u bazu
     */
    @BeforeEach
    fun setUp(){
        DatabaseFactory.init()
        DatabaseFactory.clearTable("chat")
        DatabaseFactory.clearTable("users")
        DatabaseFactory.clearTable("hospital")
        DatabaseFactory.clearTable("patient")
        DatabaseFactory.clearTable("doctor")


        hospital= Hospital(
            name = "Bolnica Pozega",
            city = "Pozega",
            address = "Visibaba bb"
        )
        hospitalServiceInterface= HospitalServiceImplementation()
        userService= UserServiceImplementation()
        patientService= PatientServiceImplementation()
        doctorService= DoctorServiceImplementation()
        repository= ChatRepositoryImplementation(
            patientService = patientService,
            doctorService = doctorService,
            service = ChatServiceImplementation()
        )
        user1= User(

            email ="pera@peric.com",
            password = "Password123!",
            role = Role.ROLE_DOCTOR
        )
        user2= User(
            email = "pera2@peric.com"
            , password = "Password123!"
            , role = Role.ROLE_DOCTOR
        )
        runBlocking {
            hospital=hospitalServiceInterface.addHospital(hospital)!!
            user1=userService.addUser(user1)!!
            user2=userService.addUser(user2)!!
        }
        doctor= Doctor(
            userId = user2.getId(),
            fullName = "Pera Peric",
            specialization = "Neurolog",
            maxPatients = 40,
            currentPatients = 20,
            hospitalId = hospital.getId()!!,
            isGeneral = false
        )
        patient= Patient(
            userId = user1.getId(),
            fullName = "Mika Mikic",
            hospitalId = hospital.getId()!!,
            jmbg = "1007002790023"
        )
        runBlocking {
            doctor= doctorService.addDoctor(doctor)!!
            patient=patientService.addPatient(patient)!!
        }
        chat= Chat(

            doctorId = doctor.getId()!!,
            patientId = patient.getId()!!
        )
    }

    /**
     * Dodavanje chat kad je null
     */
    @Test
    fun addChat_testNull()=runBlocking{

        val result=repository.addChat(null)
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Cet koji dodajete ne moze biti null",result.message)

    }
    /**
     * Dodavanje chat kad vec postoji
     */
    @Test
    fun addChat_testAlreadyExist()=runBlocking {
       repository.addChat(chat)
        val result=repository.addChat(chat)
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Cet vec postoji",result.message)
    }
    /**
     * Dodavanje chat kada je uspesno dodat
     */
    @Test
    fun addChat_successfullyTest()=runBlocking {
        val result=repository.addChat(chat)
        assertTrue(result is BaseResponse.SuccessResponse)
        assertEquals(chat,result.data)
    }

    /**
     * Pronalazak ceta po id kada je prosledjen null argument
     */
    @Test
    fun getChat_testNull()=runBlocking {
        val result=repository.getChat(null)
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Id ceta koji trazite ne moze biti null",result.message)
    }
    /**
     * Pronalazak ceta po id kada je prosledjen prazan argument
     */
    @Test
    fun getChat_testEmpty()=runBlocking {
        val result=repository.getChat("")
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Id ceta koji trazite ne moze biti prazan",result.message)
    }
    /**
     * Pronalazak ceta po id kada je prosledjen argument kao id ceta koji ne postoji u bazi
     */
    @Test
    fun getChat_testDoesntExist()=runBlocking {
        val result=repository.getChat("9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9")
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Cet nije pronadjen",result.message)
    }
    /**
     * Pronalazak ceta po id kada je prosledjen argument koji je id ceta koji postoji
     */
    @Test
    fun getChat_testExist()=runBlocking {
        val chatAdded=(repository.addChat(chat) as BaseResponse.SuccessResponse).data!!
        val result=repository.getChat(chatAdded.getId())
        assertTrue(result is BaseResponse.SuccessResponse)
        assertEquals(chatAdded,result.data)
    }

    /**
     * Pronalazak cetova za pacijenta kada je prosledjen null za njegov id
     */
    @Test
    fun getChatsForPatient_testNull()=runBlocking {
        val result=repository.getChatsForPatient(null)
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Id pacijenta cije cetove  trazite ne moze biti null",result.message)
    }
    /**
     * Pronalazak cetova za pacijenta kada je prosledjen prazan string za njegov id
     */
    @Test
    fun getChatsForPatient_testEmpty()=runBlocking {
        val result=repository.getChatsForPatient("")
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Id pacijenta cije cetove  trazite ne moze biti prazan",result.message)
    }
    /**
     * Pronalazak cetova za pacijenta kada je prosledjen njegov id koji ne postoji u bazi
     */
    @Test
    fun getChatForPatient_testPatientDoesntExist()=runBlocking {
        val result=repository.getChatsForPatient("9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9")
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Pacijent ne postoji",result.message)
    }
    /**
     * Pronalazak cetova za pacijenta kada je prosledjen njegov id za koji nema chatova
     */
    @Test
    fun getChatForPatient_testChatsDoesntExist()=runBlocking {

        val result=repository.getChatsForPatient(patient.getId())
        println(result)
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Cetovi za ovog pacijenta ne postoje",result.message)
    }
    /**
     * Pronalazak cetova za pacijenta kada je prosledjen id pacijenta koji ima cetova
     */
    @Test
    fun getChatsForPatient_testSuccessfullyFind()=runBlocking {
        repository.addChat(chat)
        val result=repository.getChatsForPatient(patient.getId())
        assertTrue(result is ListResponse.SuccessResponse)
        assertEquals(1,result.data?.size)
    }
    /**
     * Pronalazak cetova za lekara kada je prosledjen null za njegov id
     */
    @Test
    fun getChatsForDoctor_testNull()=runBlocking {
        val result=repository.getChatsForDoctor(null)
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Id lekara cije cetove  trazite ne moze biti null",result.message)
    }
    /**
     * Pronalazak cetova za lekara kada je prosledjen prazan string za njegov id
     */
    @Test
    fun getChatsForDoctor_testEmpty()=runBlocking {
        val result=repository.getChatsForDoctor("")
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Id lekara cije cetove  trazite ne moze biti prazan",result.message)
    }
    /**
     * Pronalazak cetova za lekara kada je prosledjen njegov id koji ne postoji u bazi
     */
    @Test
    fun getChatForDoctor_testPatientDoesntExist()=runBlocking {
        val result=repository.getChatsForDoctor("9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9")
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Lekar ne postoji",result.message)
    }
    /**
     * Pronalazak cetova za lekara kada je prosledjen njegov id za koji nema chatova
     */
    @Test
    fun getChatForDoctor_testChatsDoesntExist()=runBlocking {

        val result=repository.getChatsForDoctor(doctor.getId())
        println(result)
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Cetovi za ovog lekara ne postoje",result.message)
    }
    /**
     * Pronalazak cetova za lekara kada je prosledjen id lekara koji ima cetova
     */
    @Test
    fun getChatsForDoctor_testSuccessfullyFind()=runBlocking {
        repository.addChat(chat)
        val result=repository.getChatsForDoctor(doctor.getId())
        assertTrue(result is ListResponse.SuccessResponse)
        assertEquals(1,result.data?.size)
    }
}