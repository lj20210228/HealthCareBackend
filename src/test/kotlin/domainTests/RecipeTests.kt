package com.example.domainTests

import com.example.domain.Recipe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertTrue


/**
 * Klasa koja sarzi testove za klasu [Recipe]
 * @property recipe Instanca klase [Recipe] koja ce se koristiti za testove
 */
class RecipeTests {

    var recipe: Recipe?=null

    /**
     * Pre svakog testa se [recipe] Inicijalizuje na ispravne vrednosti
     */
    @BeforeEach
    fun setUp(){
        recipe= Recipe("1","1","1","Eftil 400mg",2,"1 tableta ujutru i 1 uvece", LocalDate.of(2025,9,24))

    }

    /**
     * Posle svakog testa se [recipe] inicijalizuje na null, kako ne bi dolazilo do curenja memorije
     */
    @AfterEach
    fun tearDown(){
        recipe=null
    }

    /**
     * Funkcija koja testira getId metodu
     */
    @Test
    fun getId_test(){
        assertEquals("1",recipe?.getId())
    }

    /**
     * Funkcija koja testira setId metodu sa prodledjenim null argumentom,
     * ako je bacen [NullPointerException] test prolazi
     */
    @Test
    fun setId_null(){
        assertThrows<NullPointerException> { recipe?.setId(null)}
    }
    /**
     * Funkcija koja testira setId metodu sa prodledjenim praznim stringom kao argumentom,
     * ako je bacen [IllegalArgumentException] test prolazi
     */

    @Test
    fun setId_prazanString_test(){
        assertThrows<IllegalArgumentException> { recipe?.setId("") }
    }

    /**
     * Funkcija koja testira metodu setId, sa ispravnim argumentom,
     * ako je dodeljena vrednost, jednaka vracenoj, test prolazi
     */
    @Test
    fun setId_ispravno_test(){
        recipe?.setId("2")
        assertEquals("2",recipe?.getId())
    }
    /**
     * Funkcija koja testira getPatientId metodu
     */
    @Test
    fun getPatientId_test(){
        assertEquals("1",recipe?.getPatientId())
    }
    /**
     * Funkcija koja testira setPatientId metodu sa prodledjenim null argumentom,
     * ako je bacen [NullPointerException] test prolazi
     */
    @Test
    fun setPatientId_null(){
        assertThrows<NullPointerException> { recipe?.setPatientId(null)}
    }
    /**
     * Funkcija koja testira setPatientId metodu sa prodledjenim praznim stringom kao argumentom,
     * ako je bacen [IllegalArgumentException] test prolazi
     */
    @Test
    fun setPatientId_prazanString_test(){
        assertThrows<IllegalArgumentException> { recipe?.setPatientId("") }
    }
    /**
     * Funkcija koja testira metodu setPatientId, sa ispravnim argumentom,
     * ako je dodeljena vrednost, jednaka vracenoj, test prolazi
     */
    @Test
    fun setPatientId_ispravno_test(){
        recipe?.setPatientId("2")
        assertEquals("2",recipe?.getPatientId())
    }
    /**
     * Funkcija koja testira getDoctorId metodu
     */
    @Test
    fun getDoctorId_test(){
        assertEquals("1",recipe?.getDoctorId())
    }
    /**
     * Funkcija koja testira setDoctorId metodu sa prodledjenim null argumentom,
     * ako je bacen [NullPointerException] test prolazi
     */
    @Test
    fun setDoctorId_null(){
        assertThrows<NullPointerException> { recipe?.setDoctorId(null)}
    }

    /**
     * Funkcija koja testira setDoctorId metodu sa prodledjenim praznim stringom kao argumentom,
     * ako je bacen [IllegalArgumentException] test prolazi
     */
    @Test
    fun setDoctor_prazanString_test(){
        assertThrows<IllegalArgumentException> { recipe?.setDoctorId("") }
    }
    /**
     * Funkcija koja testira metodu setDoctorId, sa ispravnim argumentom,
     * ako je dodeljena vrednost, jednaka vracenoj, test prolazi
     */
    @Test
    fun setDoctorId_ispravno_test(){
        recipe?.setDoctorId("2")
        assertEquals("2",recipe?.getDoctorId())
    }
    /**
     * Funkcija koja testira getMedication metodu
     */
    @Test
    fun getMedication_test(){
        assertEquals("Eftil 400mg",recipe?.getMedication())
    }
    /**
     * Funkcija koja testira setMedication metodu sa prodledjenim null argumentom,
     * ako je bacen [NullPointerException] test prolazi
     */
    @Test
    fun setMedication_null_test(){
        assertThrows<NullPointerException> {
            recipe?.setMedication(null)
        }
    }
    /**
     * Funkcija koja testira setMedication metodu sa prodledjenim praznim stringom kao argumentom,
     * ako je bacen [IllegalArgumentException] test prolazi
     */
    @Test
    fun setMedication_prazanString(){
        assertThrows<IllegalArgumentException> { recipe?.setMedication("") }
    }
    /**
     * Funkcija koja testira metodu setMedication, sa ispravnim argumentima,
     * ako je dodeljena vrednost, jednaka vracenoj, test prolazi
     */
    @ParameterizedTest
    @CsvSource(
        "Brufen 400mg",
        "Panklav",
        "Paracetamol"
    )
    fun setPedication_ispravno(medication: String){
        recipe?.setMedication(medication)
        assertEquals(medication,recipe?.getMedication())
    }
    /**
     * Funkcija koja testira getQuantity metodu
     */
    @Test
    fun getQuantityTest(){
        assertEquals(2,recipe?.getQuantity())
    }
    /**
     * Funkcija koja testira setQuantity metodu sa prodledjenim null argumentom,
     * ako je bacen [NullPointerException] test prolazi
     */
    @Test
    fun setQuantity_null_test(){
        assertThrows<NullPointerException> {
            recipe?.setQuantity(null)
        }
    }
    /**
     * Funkcija koja testira setQuantity metodu sa prodledjenim brojem manjim od 1 kao argumentom,
     * ako je bacen [IllegalArgumentException] test prolazi
     */
    @Test
    fun setQuantity_manjeod1(){
        assertThrows<IllegalArgumentException> { recipe?.setQuantity(0) }
    }
    /**
     * Funkcija koja testira metodu setQuantity, sa ispravnim argumentima,
     * ako je dodeljena vrednost, jednaka vracenoj, test prolazi
     */
    @ParameterizedTest
    @CsvSource(
        "1",
        "2",
        "4",
        "60"
    )
    fun setQuantity_ispavno_test(quantity:Int){
        recipe?.setQuantity(quantity)
        assertEquals(quantity,recipe?.getQuantity())
    }
    /**
     * Funkcija koja testira getInstructions metodu
     */
    @Test
    fun getInstructions_test(){
        assertEquals("1 tableta ujutru i 1 uvece",recipe?.getInstructions())
    }
    /**
     * Funkcija koja testira setInstructions metodu sa prodledjenim null argumentom,
     * ako je bacen [NullPointerException] test prolazi
     */
    @Test
    fun setInstructions_null_test(){
        assertThrows<NullPointerException> {
            recipe?.setInstructions(null)
        }
    }
    /**
     * Funkcija koja testira setInstructions metodu sa prodledjenim praznim stringom kao argumentom,
     * ako je bacen [IllegalArgumentException] test prolazi
     */
    @Test
    fun setInstructions_prazanString(){
        assertThrows<IllegalArgumentException> { recipe?.setInstructions("") }
    }
    /**
     * Funkcija koja testira metodu setInstructions, sa ispravnim argumentima,
     * ako je dodeljena vrednost, jednaka vracenoj, test prolazi
     */
    @ParameterizedTest
    @CsvSource(
        "Ne kutija nego tableta od 20mg",
        "1 ujutru posle jela i popodne posle rucka",
        "1 pred spavanje"
    )
    fun setInstructions_ispravno(instructions: String){
        recipe?.setInstructions(instructions)
        assertEquals(instructions,recipe?.getInstructions())
    }


    /**
     * Funkcija koja testira getExpiredDate metodu
     */
    @Test
    fun getExpiredDate_test(){
        assertEquals(LocalDate.of(2025,9,24),recipe?.getExpiredDate())
    }
    /**
     * Funkcija koja testira setExpiredDateTest metodu sa prodledjenim null argumentom,
     * ako je bacen [NullPointerException] test prolazi
     */
    @Test
    fun setExpiredDate_test_null(){
        assertThrows<NullPointerException> { recipe?.setDateExpired(null) }
    }
    /**
     * Funkcija koja testira setExpiredDateTest metodu sa prodledjenim praznim stringom kao argumentom,
     * ako je bacen [IllegalArgumentException] test prolazi
     */
    @Test
    fun setExpiredDate_test_proslost(){
        assertThrows<IllegalArgumentException> { recipe?.setDateExpired(LocalDate.of(2025,8,14)) }
    }
    /**
     * Funkcija koja testira metodu setExpiredDate, sa ispravnim argumentima,
     * ako je dodeljena vrednost, jednaka vracenoj, test prolazi
     */
    @ParameterizedTest
    @CsvSource(
        "2025-11-22",
        "2025-11-12",
        "2026-01-21"
    )
    fun setExpired_ispravno(date: LocalDate){
        recipe?.setDateExpired(date)
        assertEquals(date,recipe?.getExpiredDate())
    }


    /**
     * Metoda koja prosledjuje sve argumente za [Recipe] objekat, kao i ocekivani [boolean] rezultat ,
     * ako se ti rezultati poklapaju sa stvarnim testovi prolaze
     */
    @ParameterizedTest
    @CsvSource(
        "1,1,1,Eftil 400mg,2,1 tableta ujutru i 1 uvece,2025-09-24,true",
        "1,2,1,Eftil 400mg,2,1 tableta ujutru i 1 uvece,2025-09-24,false",
        "1,1,2,Eftil 400mg,2,1 tableta ujutru i 1 uvece,2025-09-24,false",
        "1,1,1,Eftil 200mg,2,1 tableta ujutru i 1 uvece,2025-09-24,false",
        "1,1,1,Eftil 400mg,1,1 tableta ujutru i 1 uvece,2025-09-24,false",
        "1,1,1,Eftil 400mg,2,1 tableta ujutru i 2 uvece,2025-09-24,false",
        "1,1,1,Eftil 400mg,2,1 tableta ujutru i 1 uvece,2025-09-25,false"
    )
    fun equals_Test_nisu_jednaki(id: String,patientId: String,doctorId: String,medication: String,
                               quantity: Int,instructions: String,date: LocalDate,boolean: Boolean  ){
        val recipe2= Recipe(id,patientId,doctorId,medication,quantity,instructions,date)
        assertEquals(boolean, recipe?.equals(recipe2))

    }

    /**
     * Metoda testrira metodu hashCode, ukoliko su 2 hashCode jednaka, metoda prolazi
     */
    @Test
    fun hashCode_test(){
        val recipe2= Recipe("1","1","1","Eftil 400mg",2,"1 tableta ujutru i 1 uvece", LocalDate.of(2025,9,24))

        assertEquals(recipe2.hashCode(),recipe?.hashCode())
    }

    /**
     * Metoda testira toString metodu, tako sto proverava da li se svaki pojedinacni argument
     * nalazi u celog stringu objekta [Recipe]
     */
    @Test
    fun toString_test(){
        val string=recipe?.toString()
        assertTrue(string?.contains("1")!!)
        assertTrue(string.contains("1"))

        assertTrue(string.contains("1"))

        assertTrue(string.contains("2"))
        assertTrue(string.contains("1 tableta ujutru i 1 uvece"))
        assertTrue(string.contains("2025-09-24"))
        assertTrue(string.contains("Eftil 400mg"))
    }





}