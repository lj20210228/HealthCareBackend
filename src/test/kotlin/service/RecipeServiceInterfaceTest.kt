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

/**
 * Klasa koja testira metode [RecipeServiceInterface]
 * @see RecipeServiceInterface
 * @author Lazar Janković
 * @see RecipeServiceImplementationTest
 * @property service Instanca [RecipeServiceInterface]
 * @property file Fajl koji simulira bazu
 * @property recipe Instanca klase recept koji ce se koristiti u testovima
 */
abstract class RecipeServiceInterfaceTest {
    /**
     * Funkcija koja dohvata koje je klase objekat [RecipeServiceInterface]
     */
    abstract fun getInstance(): RecipeServiceInterface
    var service: RecipeServiceInterface?=null
    val file= File("recipes.json")
    private lateinit var recipe: Recipe

    /**
     * Inicijalizacija parametara pre pokretanja svakog testa
     *
     */
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
    }/**
     * Inicijalizacija parametara posle izrvsavanja svakog testa
     *
     */
    @AfterEach
    fun tearDown()
    {
        service=null
        file.writeText("")

    }

    /**
     * Test metoda za dodavanje recepata kada je prosledjeni argument null,
     * ako se baci [NullPointerException] test prolazi
     */
    @Test
    fun addRecipeTest_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                service?.addRecipe(null)
            }
        }

    }
    /**
     * Test metoda za dodavanje recepata kada  prosledjeni recept vec postoji ,
     * ako se baci [IllegalArgumentException] test prolazi
     */
    @Test
    fun addRecipeTest_receptPostoji(){

        runBlocking {

            service?.addRecipe(recipe)
            assertThrows < IllegalArgumentException>{
                service?.addRecipe(recipe)
            }
        }
    }
    /**
     * Test metoda za dodavanje recepata kada je prosledjeni recept ispravan i ne postoji ,
     * ako je prosledjeni recept isti kao ovaj koji vraca metoda addRecipe test prolazi
     */
    @Test
    fun addRecipe_uspesnoDodavanje(){

        runBlocking {
            val added=service?.addRecipe(recipe)
            assertEquals(added,recipe)


        }
    }

    /**
     * Test za metodu getAllRecipesForDoctor, kada je prosledjeni argument null,
     * ako se baci [NullPointerException] test prolazi
     */
    @Test
    fun getAllRecipesForDoctorTest_null(){
        runBlocking  {
            assertThrows<NullPointerException> {
                service?.getAllRecipesForDoctor(null)
            }


        }
    }
    /**
     * Test za metodu getAllRecipesForDoctor, kada je prosledjeni argument prazan string,
     * ako se baci [IllegalArgumentException] test prolazi
     */
    @Test
    fun getAllRecipesForDoctorTest_prazanString(){
        runBlocking  {
            assertThrows<IllegalArgumentException> {
                service?.getAllRecipesForDoctor("")
            }


        }
    }
    /**
     * Test za metodu getAllRecipesForDoctor, kada je prosledjeni argument ispravan ali ne postoje recepti za tog doktora,
     * ako se vrati prazna lista test prolazi
     */
    @Test
    fun getALlRecipesForDoctorTest_nemaRecepata(){
        runBlocking {
            assertEquals(emptyList(),service?.getAllRecipesForDoctor("44"))
        }
    }
    /**
     * Test za metodu getAllRecipesForDoctor, kada je prosledjeni argument ispravan i postoje recepti za tog doktora,
     * ako se vrati lista recepata test prolazi
     */
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
    /**
     * Test za metodu getAllRecipesForPatient, kada je prosledjeni argument null,
     * ako se baci [NullPointerException] test prolazi
     */
    @Test
    fun getAllRecipesForPatientTest_null(){
        runBlocking  {
            assertThrows<NullPointerException> {
                service?.getAllRecipesForPatient(null)
            }


        }
    }
    /**
     * Test za metodu getAllRecipesForPatient, kada je prosledjeni argument prazan string,
     * ako se baci [IllegalArgumentException] test prolazi
     */
    @Test
    fun getAllRecipesForPatientTest_prazanString(){
        runBlocking  {
            assertThrows<IllegalArgumentException> {
                service?.getAllRecipesForPatient("")
            }


        }
    }
    /**
     * Test za metodu getAllRecipesForPatient, kada je prosledjeni argument ispravan ali ne postoje recepti za tog pacijenta,
     * ako se vrati prazna lista test prolazi
     */
    @Test
    fun getALlRecipesForPatientTest_nemaRecepata(){
        runBlocking {
            assertEquals(emptyList(),service?.getAllRecipesForPatient("44"))
        }
    }
    /**
     * Test za metodu getAllRecipesForPatient, kada je prosledjeni argument ispravan i postoje recepti za tog pacijenta,
     * ako se vrati lista recepata test prolazi
     */
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

    /**
     * Test koji testira metodu getRecipeForId kada je prosledjeni argument null, ako se baci
     * [NullPointerException] test prolazi
     */
    @Test
    fun getRecipeForIdTest_null(){
        runBlocking  {
            assertThrows<NullPointerException> {
                service?.getRecipeForId(null)
            }


        }
    }
    /**
     * Test koji testira metodu getRecipeForId kada je prosledjeni argument prazan string, ako se baci
     * [IllegalArgumentException] test prolazi
     */
    @Test
    fun getRecipeFOrIdTest_prazanString(){
        runBlocking  {
            assertThrows<IllegalArgumentException> {
                service?.getRecipeForId("")
            }


        }
    }
    /**
     * Test koji testira metodu getRecipeForId kada je prosledjeni argument ispravan, ali nema tog recepta,
     * ako se vrati null test prolazi
     */
    @Test
    fun getRecipeForIdTest_nemaRecepata(){
        runBlocking {
            assertEquals(null,service?.getRecipeForId("44"))
        }
    }
    /**
     * Test koji testira metodu getRecipeForId kada je prosledjeni argument ispravan, i pronadjen je taj recept,
     * ako se vrati recept koji je prethodno dodat, test prolazi
     */
    @Test
    fun  getRecipeForIdTest_uspesno(){
        runBlocking {

            service?.addRecipe(recipe)

            assertEquals(recipe,service?.getRecipeForId("1"))
        }
    }

    /**
     * Test za metodu getAllRecipesForMedication, kada je prosledjeni argument null,
     * ako se baci [NullPointerException] test prolazi
     */
    @Test
    fun getAllRecipesForMedicationTest_null(){
        runBlocking  {
            assertThrows<NullPointerException> {
                service?.getRecipesForMedication(null)
            }


        }
    }
    /**
     * Test za metodu getAllRecipesForMedication, kada je prosledjeni argument prazan string,
     * ako se baci [IllegalArgumentException] test prolazi
     */
    @Test
    fun getAllRecipesForMedicationTest_prazanString(){
        runBlocking  {
            assertThrows<IllegalArgumentException> {
                service?.getRecipesForMedication("")
            }


        }
    }
    /**
     * Test za metodu getAllRecipesForMedication, kada je prosledjeni argument ispravan ali ne postoje recepti sa tim lekom,
     * ako se vrati prazna lista test prolazi
     */
    @Test
    fun getALlRecipesForMedicationTest_nemaRecepata(){
        runBlocking {
            assertEquals(emptyList(),service?.getRecipesForMedication("Eftil"))
        }
    }
    /**
     * Test za metodu getAllRecipesForPatient, kada je prosledjeni argument ispravan i postoje recepti za taj lek,
     * ako se vrati lista recepata test prolazi
     */
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
    /**
     * Test za metodu getAllValidRecipesForDoctor, kada je prosledjeni argument null,
     * ako se baci [NullPointerException] test prolazi
     */
    @Test
    fun getAllValidRecipesForDoctorTest_null(){
        runBlocking  {
            assertThrows<NullPointerException> {
                service?.getAllValidRecipesForDoctor(null)
            }


        }
    }
    /**
     * Test za metodu getAllValidRecipesForDoctor, kada je prosledjeni argument prazan string,
     * ako se baci [IllegalArgumentException] test prolazi
     */
    @Test
    fun getAllValidRecipesForDoctorTest_prazanString(){
        runBlocking  {
            assertThrows<IllegalArgumentException> {
                service?.getAllValidRecipesForDoctor("")
            }


        }
    }
    /**
     * Test za metodu getAllValidRecipesForDoctor, kada je prosledjeni argument ispravan ali ne postoje recepti za tog doktora, ili su svi istekli,
     * ako se vrati prazna lista test prolazi
     */
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
    /**
     * Test za metodu getAllValidRecipesForDoctor, kada je prosledjeni argument ispravan i postoje recepti za tog doktora, i nisu istekli,
     * ako se vrati lista recepata test prolazi
     */
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
    /**
     * Test za metodu getAllValidRecipesForPatient, kada je prosledjeni argument null,
     * ako se baci [NullPointerException] test prolazi
     */
    @Test
    fun getAllValidRecipesForPatientTest_null(){
        runBlocking  {
            assertThrows<NullPointerException> {
                service?.getAllValidRecipesForPatient(null)
            }


        }
    }
    /**
     * Test za metodu getAllValidRecipesForPatient, kada je prosledjeni argument prazan string,
     * ako se baci [IllegalArgumentException] test prolazi
     */
    @Test
    fun getAllValidRecipesForPatientTest_prazanString(){
        runBlocking  {
            assertThrows<IllegalArgumentException> {
                service?.getAllValidRecipesForPatient("")
            }


        }
    }
    /**
     * Test za metodu getAllValidRecipesForPatient, kada je prosledjeni argument ispravan ali ne postoje recepti za tog pacijenta, ili su svi istekli
     * ako se vrati prazna lista test prolazi
     */
    @Test
    fun getALlValidRecipesForPatientTest_nemaRecepata(){
        runBlocking {
            assertEquals(emptyList(),service?.getAllValidRecipesForPatient("44"))
        }
    }
    /**
     * Test za metodu getAllValidRecipesForPatient, kada je prosledjeni argument ispravan i postoje recepti za tog pacijenta, i nisu istekli,
     * ako se vrati lista recepata test prolazi
     */
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