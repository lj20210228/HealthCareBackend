package com.example.domainTests

import com.example.domain.Doctor
import com.example.domain.Hospital
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Klasa koja testira metode [Doctor] klase
 * @property doctor [Doctor] objekat koji se koristi u testovima
 */
class DoctorTests{

    private var doctor: Doctor?=null

    /**
     * Pre svakog testa se [doctor] Inicijalizuje na ispravne vrednosti
     */
    @BeforeEach
    fun setUp(){
        doctor= Doctor("1","2","Pera Peric","Neurolog",20,0,"3",false)
    }

    /**
     * Posle svakog testa se [doctor] Inicijalizuje na null, kako ne bi doslo do curenja memorije
     */
    @AfterEach
    fun tearDown(){
        doctor=null
    }


    /**
     * Funkcija koja testira funkciju getId
     */
    @Test
    fun getId_test(){
        assertEquals("1",doctor?.getId())
    }

    /**
     * Funkcija koja testira metodu za dodeljivanje null vrednosti id lekara
     * @throws NullPointerException Test je prosao ako ce baci izuzetak
     */
    @Test
    fun setId_nullTest(){
        assertThrows<NullPointerException> {
            doctor?.setId(null)
        }
    }
    /**
     * Funkcija koja testira metodu za dodeljivanje praznog stringa id lekara
     * @throws IllegalArgumentException Ako metoda baci izuzetak test prolazi
     */
    @Test
    fun setId_prazanTest(){
        assertThrows<IllegalArgumentException> {
            doctor?.setId("")
        }
    }

    /**
     * Funckija koja testira metodu za dodeljivanje ispravne vrednosti id lekara
     */
    @Test
    fun setId_ispravanTest(){
        doctor?.setId("2")
        assertEquals("2",doctor?.getId())
    }

    /**
     * Funkcija koja testira funkciju getUserId
     */
    @Test
    fun getUserId_test(){
        assertEquals("2",doctor?.getUserId())
    }

    /**
     * Funkcija koja testira metodu za dodeljivanje null vrednosti userId parametra
     * @throws NullPointerException Test je prosao ako ce baci izuzetak
     */
    @Test
    fun setUserId_nullTest(){
        assertThrows<NullPointerException> {
            doctor?.setUserId(null)
        }
    }
    /**
     * Funkcija koja testira metodu za dodeljivanje praznog stringa userId parametra
     * @throws IllegalArgumentException Ako metoda baci izuzetak test prolazi
     */
    @Test
    fun setUserId_prazanTest(){
        assertThrows<IllegalArgumentException> {
            doctor?.setUserId("")
        }
    }

    /**
     * Funckija koja testira metodu za dodeljivanje ispravne vrednosti userId
     */
    @Test
    fun setUserId_ispravanTest(){
        doctor?.setUserId("2")
        assertEquals("2",doctor?.getUserId())
    }

    /**
     * Test za metodu za vracanje imena i prezimena lekara
     */
    @Test
    fun getFullName_test(){
        assertEquals("Pera Peric",doctor?.getFullName())
    }

    /**
     * Test za metodu za dodelu imena i prezimena lekaru, za prosledjenom null vrednoscu
     * @throws NullPointerException Ako Test uhvati izuzetak prosao je
     */
    @Test
    fun setFullName_null(){
        assertThrows<NullPointerException>
        {
            doctor?.setFullName(null)
        }
    }
    /**
     * Test za metodu za dodelu imena i prezimena lekaru, za prosledjenim argumentima bez razmaka
     * @throws IllegalArgumentException Ako Test uhvati izuzetak prosao je
     */
    @ParameterizedTest
    @CsvSource(
        "''",
        "PeraPeric"
    )
    fun setImePrezime_nemaRazmaka(fullName: String){
        assertThrows<IllegalArgumentException> {
            doctor?.setFullName(fullName)
        }

    }
    /**
     * Test za metodu za dodelu imena i prezimena lekaru, za prosledjenim
     * argumentima koji ne sadrze samo slpva
     * @throws IllegalArgumentException Ako Test uhvati izuzetak prosao je
     */
    @ParameterizedTest
    @CsvSource(
        "Pera Peric !",
        "Pera Peric 2",

    )
    fun setFullName_nisuSamoSlova(fullName: String){
        assertThrows<IllegalArgumentException> {
            doctor?.setFullName(fullName)
        }

    }
    /**
     * Test za metodu za dodelu imena i prezimena lekaru, za prosledjenim ispravnim argumentima,
     * ako su dodeljeni argumenti jednaki prosledjenim test prolazi
     */
    @ParameterizedTest
    @CsvSource(
        "Pera Peric",
        "Pera Perić",
        "Ivan Ivanović"

    )
    fun setFullName_samoSlova(fullName: String){
        doctor?.setFullName(fullName)
        assertEquals(fullName,doctor?.getFullName())

    }

    /**
     * Test za metodu za proveru funkcije koja vraca specijalizaciju lekara
     */
    @Test
    fun getSpecialization(){
        assertEquals("Neurolog",doctor?.getSpecialization())

    }

    /**
     * Test za metodu za dodelu specijalizacije lekaru, za prosledjenom null vrednoscu
     * @throws NullPointerException Ako Test uhvati izuzetak prosao je
     */
    @Test
    fun setSpecialization_null(){
        assertThrows<NullPointerException> { doctor?.setSpecialization(null)}
    }
    /**
     * Test za metodu za dodelu specializacije, za prosledjenom praznom vrednoscu
     * @throws IllegalArgumentException Ako Test uhvati izuzetak prosao je
     */
    @Test
    fun setSpecialization_prananString(){
        assertThrows<IllegalArgumentException> { doctor?.setSpecialization("")}

    }
    /**
     * Test za proveru metode
     * za dodelu specijalizacije lekaru, za prosledjenom vresnoscu koja ne sadrzi samo slova
     * @throws IllegalArgumentException Ako Test uhvati izuzetak prosao je
     */
    @Test
    fun setSpecialization_nisuSamoSlova(){
        assertThrows<IllegalArgumentException> { doctor?.setSpecialization("ORL@1")}

    }
    /**
     * Test za metodu za dodelu imena i prezimena lekaru, za prosledjenom ispravnom vrednoscu
     */
    @Test
    fun setSpecialization_uspesnaDodela(){
        doctor?.setSpecialization("Ortoped")
        assertEquals("Ortoped",doctor?.getSpecialization())
    }

    /**
     * Test za metodu za vracanje maksimalnog broja pacijenata kojima lekar moze biti izabrani
     */
    @Test
    fun getMaxPatients(){
        assertEquals(20,doctor?.getMaxPatients())
    }

    /**
     * Test za metodu setMaxPatients sa prosledjenom 0 vrednoscu
     * @throws IllegalArgumentException Test prolazi ako se baci izuzetak
     */
    @Test
    fun setMaxPatients_nulaTest(){
        assertThrows<IllegalArgumentException> {
            doctor?.setMaxPatients(0)
        }
    }
    /**
     * Test za metodu setMaxPatients sa prosledjenom null vrednoscu
     * @throws NullPointerException Test prolazi ako se baci izuzetak
     */
    @Test
    fun setMaxPatients_nullTest(){
        assertThrows<NullPointerException> {
            doctor?.setMaxPatients(null)
        }
    }
    /**
     * Test za metodu za vracanje trenutnog broja pacijenata kojima lekar moze biti izabrani
     */
    @Test
    fun getCurrentPatients(){
        assertEquals(0,doctor?.getCurrentPatients())
    }


    /**
     * Test za metodu setCurrentPatients sa prosledjenom null vrednoscu
     * @throws NullPointerException Test prolazi ako se baci izuzetak
     */
    @Test
    fun setCurrentPatients_nullTest(){
        assertThrows<NullPointerException> {
            doctor?.setMaxPatients(null)
        }
    }
    /**
     * Funkcija koja testira funkciju getHospitalId
     */
    @Test
    fun getHosputitalId_test(){
        assertEquals("3",doctor?.getHospitalId())
    }

    /**
     * Funkcija koja testira metodu za dodeljivanje null vrednosti hospitalId parametra
     * @throws NullPointerException Test je prosao ako ce baci izuzetak
     */
    @Test
    fun setHospitalId_nullTest(){
        assertThrows<NullPointerException> {
            doctor?.setHospitalId(null)
        }
    }
    /**
     * Funkcija koja testira metodu za dodeljivanje praznog stringa hospitalId parametra
     * @throws IllegalArgumentException Ako metoda baci izuzetak test prolazi
     */
    @Test
    fun setHosputalId_prazanTest(){
        assertThrows<IllegalArgumentException> {
            doctor?.setHospitalId("")
        }
    }

    /**
     * Funckija koja testira metodu za dodeljivanje ispravne vrednosti hospitalId
     */
    @Test
    fun setHospitalId_ispravanTest(){
        doctor?.setHospitalId("2")
        assertEquals("2",doctor?.getHospitalId())
    }

    /**
     * Funckija koja testira metodu getIsGeneral
     */
    @Test
    fun getIsGeneral_test(){
        assertFalse(doctor?.getIsGeneral()!!)

    }

    /**
     * Funkcija koja testira metodu setIsGeneral ako je prosledjena null vrednost
     * @throws NullPointerException Ako metoda baci izuzetak test prolazi
     */
    @Test
    fun setIsGeneral_Null_test(){
        assertThrows<NullPointerException> {
            doctor?.setIsGeneral(null)
        }
    }

    /**
     * Funkcija koja testira metodu setIsGeneral, test prolazi ako
     * metoda setIsGeneral ispravno dodeli vrednosti
     */
    @ParameterizedTest
    @CsvSource
        (
                "true"
                ,"false"
                )
    fun setIsGeneral_ispravno_test(isGeneral: Boolean){
        doctor?.setIsGeneral(isGeneral)
       assertEquals(isGeneral,doctor?.getIsGeneral())
    }

    /**
     * Funkcija koja testira toString metodu i ako se sadrze svi stringovi, test prolazi
     */
    @Test
    fun toString_test(){
        val string=doctor?.toString()
        assertTrue(string?.contains("1")!!)
        assertTrue(string?.contains("2")!!)

        assertTrue(string?.contains("3")!!)

        assertTrue(string?.contains("Neurolog")!!)
        assertTrue(string?.contains("20")!!)
        assertTrue(string?.contains("0")!!)
        assertTrue(string?.contains("Pera Peric")!!)
        assertTrue(string?.contains("false")!!)
    }
    /**
     * Metoda za test equals metode, tako sto se prosledjuju argumenti, i da ocekivanu true ili false vrenost,
     * zatim se uporedju za rezultatima metode
     */
    @ParameterizedTest
    @CsvSource(

        ("1,2,true"),
        ("99,2,true"),
        ("1,3,true"),
        ("3,4,false")

    )
    fun equals_test(id: String, userId: String,expected: Boolean){
        val doctor1 = Doctor("1","2","Pera Peric","Neurolog",30,0,"3",false)
        val doctor2 = Doctor(id,userId,"Pera Peric","Neurolog",30,0,"3",false)

        assertEquals(expected, doctor1.equals(doctor2))
    }

    /**
     * Test za hashCode metodu, sa razlicitim parametrima jer hadhCode zavisi od id,userId, fullName i specializacija,
     * ako su svi ti argumenti isti, hashCode je isti, ako ne nije
     */
    @ParameterizedTest
    @CsvSource(

        ("1,2,Pera Peric,Neurolog,30,0,3,true,true"),
        ("1,2,Pera Peric,Neurolog,30,0,4,false,true"),
        ("1,2,Pera Peric,Neurolog,30,1,3,false,true"),
        ("1,2,Pera Peric,Neurolog,40,0,3,false,true"),
        ("1,2,Pera Mitic,Neurolog,40,0,3,false,false"),
        ("2,2,Pera Peric,Neurolog,40,0,3,false,false"),
        ("1,3,Pera Peric,Neurolog,40,0,3,false,false"),
        ("1,2,Pera Peric,Kardiolog,40,0,3,false,false"),
        )
    fun hashCodeTest(id: String, userId: String, fullName: String, specialization: String,maxPatients:Int
                     ,currentPatients:Int,hospitalId: String,isGeneral: Boolean,expected: Boolean){
        val doctor1 = Doctor("1","2","Pera Peric","Neurolog",30,0,"3",false)
        val doctor2 = Doctor(id,userId,fullName,specialization,maxPatients,currentPatients,hospitalId,isGeneral)

        assertEquals(expected,doctor1.hashCode().equals(doctor2.hashCode()))
    }




}
