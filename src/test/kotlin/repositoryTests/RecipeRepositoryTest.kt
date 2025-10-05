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
import com.example.response.ListResponse
import com.example.service.DoctorServiceInterface
import com.example.service.doctor.DoctorServiceImplementation
import com.example.service.hospital.HospitalServiceImplementation
import com.example.service.hospital.HospitalServiceInterface
import com.example.service.patient.PatientServiceImplementation
import com.example.service.patient.PatientServiceInterface
import com.example.service.recipe.RecipeServiceImplementation
import com.example.service.recipe.RecipeServiceInterface
import com.example.service.user.UserServiceImplementation
import com.example.service.user.UserServiceInterface
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Testovi za metode [RecipeRepository]
 * @author Lazar Jankovic
 * @see RecipeRepository
 * @see RecipeRepositoryImplementation
 * @see Recipe
 * @see UserServiceInterface
 * @see DoctorServiceInterface
 * @see PatientServiceInterface
 */
class RecipeRepositoryTest {

    private lateinit var recipeRepository: RecipeRepository
    private lateinit var recipeService: RecipeServiceInterface
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


    /**
     * Pre svakog testa se brisu kolone iz baze,
     * dodaju se useri i lekar i pacijent i bolnica koji ce se koristiti u testovima
     */
    @BeforeEach
    fun setUp(){
        DatabaseFactory.init()
        DatabaseFactory.clearTable("recipes")
        DatabaseFactory.clearTable("users")
        DatabaseFactory.clearTable("doctor")
        DatabaseFactory.clearTable("patient")
        recipeRepository= RecipeRepositoryImplementation(
            patientService = PatientServiceImplementation(),
            serviceInterface = RecipeServiceImplementation(patientService = PatientServiceImplementation())
        )
        userService= UserServiceImplementation()
        doctorService= DoctorServiceImplementation()
        patientServiceInterface= PatientServiceImplementation()
        recipeService= RecipeServiceImplementation(patientServiceInterface)

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
            medication = "Eftil 200mg",
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

    /**
     * Dodavanje recepta kada je prosledjen null argument
     */
    @Test
    fun addRecipe_testNull()= runBlocking{
        val result=recipeRepository.addRecipe(null)
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Ne mozete dodati recept bez podataka",result.message)
    }
    /**
     * Dodavanje recepta kada recept vec postoji
     */
    @Test
    fun addRecipe_alreadyExistTest()=runBlocking {
        recipeRepository.addRecipe(recipe1)
        val result=recipeRepository.addRecipe(recipe1)
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Recept za ove podatke i ovaj datum vec postoji",result.message)
    }
    /**
     * Dodavanje recepta kada recept ne postoji
     */
    @Test
    fun addRecipe_ispravno()=runBlocking {

        val result=recipeRepository.addRecipe(recipe1)
        assertTrue(result is BaseResponse.SuccessResponse)
        assertEquals(recipe1,result.data)

    }

    /**
     * Test za trazenje svih recepata za lekara kada je prosledjen null argument
     */
    @Test
    fun getAllRecipesForDoctor_testNull()=runBlocking {

        val result=recipeRepository.getAllRecipesForDoctor(null)
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Niste prosledili ispravne podatke o lekaru",result.message)
    }
    /**
     * Test za trazenje svih recepata za lekara kada nema recepata
     */
    @Test
    fun getAllRecipesForDoctor_testNemaRecepata()=runBlocking {

        val result=recipeRepository.getAllRecipesForDoctor(doctor.getId())
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Nije prepisan ni jedan recept",result.message)
    }
    /**
     * Test za trazenje svih recepata za lekara kada ima recepata
     */
    @Test
    fun getAllRecipesForDoctor_testIspravno()=runBlocking {
        recipeRepository.addRecipe(recipe1)
        recipeRepository.addRecipe(recipe2)
        val result=recipeRepository.getAllRecipesForDoctor(doctor?.getId())
        assertTrue(result is ListResponse.SuccessResponse)
        assertEquals(2,result.data?.size)
    }
    /**
     * Test za trazenje svih recepata za pacijenta kada je prosledjen null argument
     */
    @Test
    fun getAllRecipesForPatient_testNull()=runBlocking {

        val result=recipeRepository.getAllRecipesForPatient(null)
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Niste prosledili ispravne podatke o pacijentu",result.message)
    }
    /**
     * Test za trazenje svih recepata za pacijenta kada nema recepata
     */
    @Test
    fun getAllRecipesForPatient_testNemaRecepata()=runBlocking {

        val result=recipeRepository.getAllRecipesForPatient(patient.getId())
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Nije prepisan ni jedan recept",result.message)
    }
    /**
     * Test za trazenje svih recepata za lekara kada ima recepata
     */
    @Test
    fun getAllRecipesForPatient_testIspravno()=runBlocking {
        recipeRepository.addRecipe(recipe1)
        recipeRepository.addRecipe(recipe2)
        val result=recipeRepository.getAllRecipesForPatient(patient?.getId())
        assertTrue(result is ListResponse.SuccessResponse)
        assertEquals(2,result.data?.size)
    }

    /**
     * Test za trazenje recepta po id kada je prosledjen null argument
     */
    @Test
    fun getRecipeForId_testNull()=runBlocking {

        val result=recipeRepository.getRecipeForId(null)
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Niste prosledili ispravne podatke o receptu",result.message)
    }
    /**
     * Test za trazenje recepta po id kada recept ne postoji
     */
    @Test
    fun getRecipeForId_testNemaRecepta()=runBlocking {


        val result=recipeRepository.getRecipeForId("9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9")
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Recept nije pronadjen",result.message)
    }
    /**
     * Test za trazenje recepta po id kada recept postoji
     */
    @Test
    fun getRecipeForId_testIspravno()=runBlocking {
        val recipe=(recipeRepository.addRecipe(recipe1) as BaseResponse.SuccessResponse).data
        val result=recipeRepository.getRecipeForId(recipe?.getId())
        assertTrue(result is BaseResponse.SuccessResponse)
        assertEquals(recipe1,result.data)
    }

    /**
     * Test za trazenje recepata po imenu kada je prosledjen null argument
     */
    @Test
    fun getRecipesForMedication_testNull()=runBlocking {

        val result=recipeRepository.getRecipesForMedication(null)
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Niste prosledili ispravne podatke o leku",result.message)
    }
    /**
     * Test za trazenje recepata po imenu kada nema recepata
     */
    @Test
    fun getRecipesForMedication_testNemaRecepata()=runBlocking {

        val result=recipeRepository.getRecipesForMedication(recipe1.getMedication())
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Nema recepata sa ovim lekom",result.message)
    }
    /**
     * Test za trazenje recepata po imenu kada ima recepata
     */
    @Test
    fun getRecipesForMedication_testIspravno()=runBlocking {
        recipeRepository.addRecipe(recipe1)
        recipeRepository.addRecipe(recipe2)
        val result=recipeRepository.getRecipesForMedication("Eftil")
        println(result)
        assertTrue(result is ListResponse.SuccessResponse)
        assertEquals(2,result.data?.size)
    }

    /**
     * Test za trazenje recepata za lekara koji su validni kada je prosledjen null argument
     */
    @Test
    fun getAllValidRecipesForDoctor_testNull()=runBlocking {

        val result=recipeRepository.getAllValidRecipesForDoctor(null)
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Niste prosledili ispravne podatke o lekaru",result.message)
    }
    /**
     * Test za trazenje recepata za lekara koji su validni kada nema recepata
     */
    @Test
    fun getAllValidRecipesForDoctor_testNemaRecepata()=runBlocking {

        val result=recipeRepository.getAllValidRecipesForDoctor(doctor.getId())
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Nije prepisan ni jedan recept koji i dalje vazi",result.message)
    }
    /**
     * Test za trazenje recepata za lekara koji su validni kada ima recepata
     */
    @Test
    fun getAllValidRecipesForDoctor_testIspravno()=runBlocking {
        recipeRepository.addRecipe(recipe1)
        recipeRepository.addRecipe(recipe2)
        val result=recipeRepository.getAllValidRecipesForDoctor(doctor?.getId())
        assertTrue(result is ListResponse.SuccessResponse)
        assertEquals(2,result.data?.size)
    }
    /**
     * Test za trazenje recepata za pacijenta koji su validni kada je prosledjen null argument
     */
    @Test
    fun getAllValidRecipesForPatient_testNull()=runBlocking {

        val result=recipeRepository.getAllRecipesForPatient(null)
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Niste prosledili ispravne podatke o pacijentu",result.message)
    }
    /**
     * Test za trazenje recepata za pacijenta koji su validni kada nema recepata
     */
    @Test
    fun getAllValidRecipesForPatient_testNemaRecepata()=runBlocking {

        val result=recipeRepository.getAllValidRecipesForPatient(patient.getId())
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Nije prepisan ni jedan recept koji i dalje vazi",result.message)
    }
    /**
     * Test za trazenje recepata za pacijenta koji su validni kada ima recepata
     */
    @Test
    fun getAllValidRecipesForPatient_testIspravno()=runBlocking {
        recipeRepository.addRecipe(recipe1)
        recipeRepository.addRecipe(recipe2)
        val result=recipeRepository.getAllValidRecipesForPatient(patient.getId())
        assertTrue(result is ListResponse.SuccessResponse)
        assertEquals(2,result.data?.size)
    }
    /**
     * Test za trazenje recepata za pacijenta po jmbg koji su validni kada je prosledjen null argument
     */
    @Test
    fun getAllValidRecipesForPatientJmbg_testNull()=runBlocking {

        val result=recipeRepository.getAllValidRecipesForPatientForJmbg(null)
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Niste uneli ispravan jmbg",result.message)
    }
    /**
     * Test za trazenje recepata za pacijenta po jmbg koji su validni kada recepti nisu pronadjeni
     */
    @Test
    fun getAllValidRecipesForPatientJmbg_testNemaRecepata()=runBlocking {

        val result=recipeRepository.getAllValidRecipesForPatientForJmbg(patient.getJmbg())
        assertTrue(result is ListResponse.ErrorResponse)
        assertEquals("Ne postoje vazeci recepti za ovog pacijenta",result.message)
    }
    /**
     * Test za trazenje recepata za pacijenta po jmbg koji su validni kada su recepti  pronadjeni
     */
    @Test
    fun getAllValidRecipesForPatientJmbg_testIspravno()=runBlocking {
        recipeRepository.addRecipe(recipe1)
        recipeRepository.addRecipe(recipe2)
        println(patient.getId())
        val result=recipeRepository.getAllValidRecipesForPatientForJmbg(patient.getJmbg())
        println(patient.getJmbg())
        assertTrue(result is ListResponse.SuccessResponse)
        assertEquals(2,result.data?.size)
    }

    /**
     * Test za azuriranje recepta kada je prosledjen null argument
     */
    @Test
    fun editRecipe_testNull()=runBlocking {
        val result=recipeRepository.editRecipe(null)
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Niste prosledili podatke za azuriranje",result.message)

    }
    /**
     * Test za azuriranje recepta kada je prosledjen recept ne postoji
     */
    @Test
    fun editRecipe_testNePostoji()=runBlocking {
        val recipe= Recipe(
            id ="9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9" ,
            patientId = patient.getId()!!,
            doctorId =doctor.getId()!!,
            medication = "Eftil 200mg",
            quantity = 2,
            instructions = "1 ujutru i 1 uvece",
            dateExpired = LocalDate.of(2027,6,12)
        )
        val result=recipeRepository.editRecipe(recipe)
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Recept ne postoji",result.message)
    }
    /**
     * Test za azuriranje recepta kada je prosledjen recept postoji
     */
    @Test
    fun editRecipe_testPostoji()=runBlocking {
       recipe1= recipeService.addRecipe(recipe1)!!
        val recipe=recipe1.copy(medication = "Brufen 600mg")
        val result=recipeRepository.editRecipe(recipe)
        assertTrue(result is BaseResponse.SuccessResponse)
        assertEquals(recipe,result.data)
    }
    /**
     * Test za brisanje recepta kada je prosledjen null argument
     */
    @Test
    fun deleteRecipe_testNull()=runBlocking {
        val result=recipeRepository.deleteRecipe(null)
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Niste prosledili ispravne podatke za brisanje",result.message)

    }
    /**
     * Test za brisanje recepta kada je prosledjen recept ne postoji
     */
    @Test
    fun deleteRecipe_testNePostoji()=runBlocking {

        val result=recipeRepository.deleteRecipe("9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9")
        assertTrue(result is BaseResponse.ErrorResponse)
        assertEquals("Recept ne postoji",result.message)
    }
    /**
     * Test za brisanje recepta kada je prosledjen recept postoji
     */
    @Test
    fun deleteRecipe_testPostoji()=runBlocking {
        recipe1=recipeService.addRecipe(recipe1)!!
        val result=recipeRepository.deleteRecipe(recipe1.getId())
        assertTrue(result is BaseResponse.SuccessResponse)
        assertEquals("Recept je obrisan",result.message)
    }







}