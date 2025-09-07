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
            assertThrows<NullPointerException> {
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
            assertThrows<NullPointerException> {
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

}