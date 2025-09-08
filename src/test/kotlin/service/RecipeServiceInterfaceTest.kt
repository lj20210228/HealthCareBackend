package com.example.service

import com.example.domain.Recipe
import com.example.service.recipe.RecipeServiceInterface
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertTrue


abstract class RecipeServiceInterfaceTest {
    abstract fun getInstance(): RecipeServiceInterface
    var service: RecipeServiceInterface?=null
    val file= File("recipes.json")
    private lateinit var recipe: Recipe
    @BeforeEach
    fun setUp(){
        service=getInstance()
        file.writeText("")
        recipe= Recipe(
            id = "1",
            patientId = "p1",
            doctorId = "d1",
            medication = "Brufen",
            instructions = "2x dnevno",
            quantity = 10,
            dateExpired = LocalDate.now().plusDays(5)
        )
    }
    @AfterEach
    fun tearDown()
    {
        service=null
        file.writeText("")

    }
    @Test
    fun addRecipeTest_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                service?.addRecipe(null)
            }
        }
    }
    @Test
    fun addRecipeTest_receptPostoji(){

        runBlocking {

            service?.addRecipe(recipe)
            assertThrows < IllegalArgumentException>{
                service?.addRecipe(recipe)
            }
        }
    }
    @Test
    fun addRecipe_uspesnoDodavanje(){

        runBlocking {
            val added=service?.addRecipe(recipe)
            assertEquals(added,recipe)


        }
    }
    @Test
    fun getAllRecipesForDoctorTest_null(){
        runBlocking  {
            assertThrows<NullPointerException> {
                service?.getAllRecipesForDoctor(null)
            }


        }
    }
    @Test
    fun getAllRecipesForDoctorTest_prazanString(){
        runBlocking  {
            assertThrows<IllegalArgumentException> {
                service?.getAllRecipesForDoctor("")
            }


        }
    }
    @Test
    fun getALlRecipesForDoctorTest_nemaRecepata(){
        runBlocking {
            assertEquals(emptyList(),service?.getAllRecipesForDoctor("44"))
        }
    }
    @Test
    fun  getAllRecipesForDoctorTest_uspesno(){
        runBlocking {
            val recipe2=Recipe(
                id = "2",
                patientId = "p2",
                doctorId = "d1",
                medication = "Brufen",
                instructions = "2x dnevno",
                quantity = 10,
                dateExpired = LocalDate.now().plusDays(5)
            )
            service?.addRecipe(recipe)
            service?.addRecipe(recipe2)
            val list=mutableListOf<Recipe>()
            list.add(recipe)
            list.add(recipe2)
            assertEquals(list,service?.getAllRecipesForDoctor("d1"))
        }
    }
    @Test
    fun getAllRecipesForPatientTest_null(){
        runBlocking  {
            assertThrows<NullPointerException> {
                service?.getAllRecipesForPatient(null)
            }


        }
    }
    @Test
    fun getAllRecipesForPatientTest_prazanString(){
        runBlocking  {
            assertThrows<IllegalArgumentException> {
                service?.getAllRecipesForPatient("")
            }


        }
    }
    @Test
    fun getALlRecipesForPatientTest_nemaRecepata(){
        runBlocking {
            assertEquals(emptyList(),service?.getAllRecipesForPatient("44"))
        }
    }
    @Test
    fun  getAllRecipesForPatientTest_uspesno(){
        runBlocking {
            val recipe2=Recipe(
                id = "2",
                patientId = "p1",
                doctorId = "d2",
                medication = "Brufen",
                instructions = "2x dnevno",
                quantity = 10,
                dateExpired = LocalDate.now().plusDays(5)
            )
            service?.addRecipe(recipe)
            service?.addRecipe(recipe2)
            val list=mutableListOf<Recipe>()
            list.add(recipe)
            list.add(recipe2)
            assertEquals(list,service?.getAllRecipesForPatient("p1"))
        }
    }
    @Test
    fun getRecipeForIdTest_null(){
        runBlocking  {
            assertThrows<NullPointerException> {
                service?.getRecipeForId(null)
            }


        }
    }
    @Test
    fun getRecipeFOrIdTest_prazanString(){
        runBlocking  {
            assertThrows<IllegalArgumentException> {
                service?.getRecipeForId("")
            }


        }
    }
    @Test
    fun getRecipeForIdTest_nemaRecepata(){
        runBlocking {
            assertEquals(null,service?.getRecipeForId("44"))
        }
    }
    @Test
    fun  getRecipeForIdTest_uspesno(){
        runBlocking {

            service?.addRecipe(recipe)

            assertEquals(recipe,service?.getRecipeForId("1"))
        }
    }

    @Test
    fun getAllRecipesForMedicationTest_null(){
        runBlocking  {
            assertThrows<NullPointerException> {
                service?.getRecipesForMedication(null)
            }


        }
    }
    @Test
    fun getAllRecipesForMedicationTest_prazanString(){
        runBlocking  {
            assertThrows<IllegalArgumentException> {
                service?.getRecipesForMedication("")
            }


        }
    }
    @Test
    fun getALlRecipesForMedicationTest_nemaRecepata(){
        runBlocking {
            assertEquals(emptyList(),service?.getRecipesForMedication("Eftil"))
        }
    }
    @Test
    fun  getAllRecipesForMedicationTest_uspesno(){
        runBlocking {
            val recipe2=Recipe(
                id = "2",
                patientId = "p1",
                doctorId = "d2",
                medication = "Brufen",
                instructions = "2x dnevno",
                quantity = 10,
                dateExpired = LocalDate.now().plusDays(5)
            )
            service?.addRecipe(recipe)
            service?.addRecipe(recipe2)
            val list=mutableListOf<Recipe>()
            list.add(recipe)
            list.add(recipe2)
            assertEquals(list,service?.getRecipesForMedication("brufen"))
        }
    }
    @Test
    fun getAllValidRecipesForDoctorTest_null(){
        runBlocking  {
            assertThrows<NullPointerException> {
                service?.getAllValidRecipesForDoctor(null)
            }


        }
    }
    @Test
    fun getAllValidRecipesForDoctorTest_prazanString(){
        runBlocking  {
            assertThrows<IllegalArgumentException> {
                service?.getAllValidRecipesForDoctor("")
            }


        }
    }
    @Test
    fun getALlValidRecipesForDoctorTest_nemaRecepata(){
        runBlocking {
           val recipe2= Recipe(
                id = "1",
                patientId = "p1",
                doctorId = "d2",
                medication = "Brufen",
                instructions = "2x dnevno",
                quantity = 10,
                dateExpired = LocalDate.now()
            )
            service?.addRecipe(recipe2)
            assertEquals(emptyList(),service?.getAllValidRecipesForDoctor("d2"))
        }
    }
    @Test
    fun  getAllValidRecipesForDoctorTest_uspesno(){
        runBlocking {
            val recipe2=Recipe(
                id = "2",
                patientId = "p2",
                doctorId = "d1",
                medication = "Brufen",
                instructions = "2x dnevno",
                quantity = 10,
                dateExpired = LocalDate.now().plusDays(5)
            )
            service?.addRecipe(recipe)
            service?.addRecipe(recipe2)
            val list=mutableListOf<Recipe>()
            list.add(recipe)
            list.add(recipe2)
            assertEquals(list,service?.getAllValidRecipesForDoctor("d1"))
        }
    }
    @Test
    fun getAllValidRecipesForPatientTest_null(){
        runBlocking  {
            assertThrows<NullPointerException> {
                service?.getAllValidRecipesForPatient(null)
            }


        }
    }
    @Test
    fun getAllValidRecipesForPatientTest_prazanString(){
        runBlocking  {
            assertThrows<IllegalArgumentException> {
                service?.getAllValidRecipesForPatient("")
            }


        }
    }
    @Test
    fun getALlValidRecipesForPatientTest_nemaRecepata(){
        runBlocking {
            assertEquals(emptyList(),service?.getAllValidRecipesForPatient("44"))
        }
    }
    @Test
    fun  getAllValidRecipesForPatientTest_uspesno(){
        runBlocking {
            val recipe2=Recipe(
                id = "2",
                patientId = "p1",
                doctorId = "d2",
                medication = "Brufen",
                instructions = "2x dnevno",
                quantity = 10,
                dateExpired = LocalDate.now().plusDays(5)
            )
            service?.addRecipe(recipe)
            service?.addRecipe(recipe2)
            val list=mutableListOf<Recipe>()
            list.add(recipe)
            list.add(recipe2)
            assertEquals(list,service?.getAllRecipesForPatient("p1"))
        }
    }

}