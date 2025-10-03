package com.example.repositoryTests

import com.example.UserService
import com.example.database.DatabaseFactory
import com.example.domain.Doctor
import com.example.domain.Hospital
import com.example.domain.Patient
import com.example.domain.Recipe
import com.example.domain.Role
import com.example.domain.User
import com.example.repository.recipe.RecipeRepository
import com.example.repository.recipe.RecipeRepositoryImplementation
import com.example.response.BaseResponse
import com.example.service.DoctorServiceInterface
import com.example.service.doctor.DoctorServiceImplementation
import com.example.service.hospital.HospitalServiceImplementation
import com.example.service.hospital.HospitalServiceInterface
import com.example.service.patient.PatientServiceImplementation
import com.example.service.patient.PatientServiceInterface
import com.example.service.recipe.RecipeServiceImplementation
import com.example.service.user.UserServiceImplementation
import com.example.service.user.UserServiceInterface
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RecipeRepositoryTest {

    private lateinit var recipeRepository: RecipeRepository
    private lateinit var recipe1: Recipe
    private lateinit var recipe2: Recipe
    private lateinit var patient: Patient
    private lateinit var user1: User
    private lateinit var user2: User
    private lateinit var doctor: Doctor
    private lateinit var hospital: Hospital
    private lateinit var userService: UserServiceInterface
    private lateinit var doctorService: DoctorServiceInterface
    private lateinit var patientServiceInterface: PatientServiceInterface
    private lateinit var hospitalService: HospitalServiceInterface


    @BeforeEach
    fun setUp(){
        DatabaseFactory.init()
        DatabaseFactory.clearTable("recipes")
        recipeRepository= RecipeRepositoryImplementation(
            patientService = PatientServiceImplementation(),
            serviceInterface = RecipeServiceImplementation(patientService = PatientServiceImplementation())
        )
        userService= UserServiceImplementation()
        doctorService= DoctorServiceImplementation()
        patientServiceInterface= PatientServiceImplementation()
        hospitalService= HospitalServiceImplementation()
        hospital= Hospital(
            name = "Opsta bolnica",
            city = "Beograd",
            address = "Kneza Milosa 23"
        )
        user1= User(
            email = "pera@peric.com"
            , password = "PAssword123!",
            role = Role.ROLE_PATIENT
        )
        user2= User(
            email = "ivan@peric.com"
            , password = "PAssword123!",
            role = Role.ROLE_DOCTOR
        )
        runBlocking {
            hospital=hospitalService.addHospital(hospital)!!
            user1=userService.addUser(user1)!!
            user2=userService.addUser(user2)!!
        }
        doctor= Doctor(
            fullName = "Pera Peric",
            specialization = "Neurolog",
            maxPatients = 40,

            userId = user1.getId(),
            currentPatients = 5,
            hospitalId = hospital.getId()!!,
            isGeneral = false,
        )
        patient= Patient(
            fullName = "Ivan Peric",
            userId = user2.getId(),
            jmbg = "1007002790023",
            hospitalId = hospital.getId()!!
        )
        runBlocking {
            doctor=doctorService.addDoctor(doctor)!!
            patient=patientServiceInterface.addPatient(patient)!!
        }

        recipe1= Recipe(
            patientId = patient.getId()!!,
            doctorId =doctor.getId()!!,
            medication = "Eftil 400mg",
            quantity = 2,
            instructions = "1 ujutru i 1 uvece",
            dateExpired = LocalDate.of(2027,6,12)
        )
        recipe2= Recipe(
            patientId = patient.getId()!!,
            doctorId =doctor.getId()!!,
            medication = "Eftil 400mg",
            quantity = 4,
            instructions = "1 ujutru i 1 uvece",
            dateExpired = LocalDate.of(2026,6,12)
        )
    }
    @Test
    fun addRecipe_testNull()= runBlocking{
        val result=recipeRepository.addRecipe(null)
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Ne mozete dodati recept bez podataka",result.message)
    }
    @Test
    fun addRecipe_alreadyExistTest()=runBlocking {
        recipeRepository.addRecipe(recipe1)
        val result=recipeRepository.addRecipe(recipe1)
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Recept za ove podatke i ovaj datum vec postoji",result.message)
    }
    @Test
    fun addRecipe_ispravno()=runBlocking {

        val result=recipeRepository.addRecipe(recipe1)
        assertTrue(result is BaseResponse.SuccessResponse)
        assertEquals(recipe1,result.data)

    }
}