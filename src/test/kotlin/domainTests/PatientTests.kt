package com.example.domainTests

import com.example.domain.Patient
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals
import kotlin.test.assertTrue
/**
 * Klasa koja testira metode [Patient] klase
 * @property patient [Patient] objekat koji se koristi u testovima
 */
class PatientTests {
    private var patient: Patient?=null

    /**
     * Pre svakog testa se [patient] Inicijalizuje na ispravne vrednosti
     */
    @BeforeEach
    fun setUp(){
        patient= Patient("1","2","Pera Peric","1","1007002790023")
    }

    /**
     * Posle svakog testa se [patient] Inicijalizuje na null, kako ne bi doslo do curenja memorije
     */
    @AfterEach
    fun tearDown(){
        patient=null
    }


    /**
     * Funkcija koja testira funkciju getId
     */
    @Test
    fun getId_test(){
        assertEquals("1",patient?.getId())
    }

    /**
     * Funkcija koja testira metodu za dodeljivanje null vrednosti id pacijenta
     * @throws NullPointerException Test je prosao ako ce baci izuzetak
     */
    @Test
    fun setId_nullTest(){
        assertThrows<NullPointerException> {
            patient?.setId(null)
        }
    }
    /**
     * Funkcija koja testira metodu za dodeljivanje praznog stringa id pacijenta
     * @throws IllegalArgumentException Ako metoda baci izuzetak test prolazi
     */
    @Test
    fun setId_prazanTest(){
        assertThrows<IllegalArgumentException> {
            patient?.setId("")
        }
    }

    /**
     * Funckija koja testira metodu za dodeljivanje ispravne vrednosti id pacijenta
     */
    @Test
    fun setId_ispravanTest(){
        patient?.setId("2")
        assertEquals("2",patient?.getId())
    }

    /**
     * Funkcija koja testira funkciju getUserId
     */
    @Test
    fun getUserId_test(){
        assertEquals("2",patient?.getUserId())
    }

    /**
     * Funkcija koja testira metodu za dodeljivanje null vrednosti userId parametra
     * @throws NullPointerException Test je prosao ako ce baci izuzetak
     */
    @Test
    fun setUserId_nullTest(){
        assertThrows<NullPointerException> {
            patient?.setUserId(null)
        }
    }
    /**
     * Funkcija koja testira metodu za dodeljivanje praznog stringa userId parametra
     * @throws IllegalArgumentException Ako metoda baci izuzetak test prolazi
     */
    @Test
    fun setUserId_prazanTest(){
        assertThrows<IllegalArgumentException> {
            patient?.setUserId("")
        }
    }

    /**
     * Funckija koja testira metodu za dodeljivanje ispravne vrednosti userId
     */
    @Test
    fun setUserId_ispravanTest(){
        patient?.setUserId("2")
        assertEquals("2",patient?.getUserId())
    }

    /**
     * Test za metodu za vracanje imena i prezimena pacijenta
     */
    @Test
    fun getFullName_test(){
        assertEquals("Pera Peric",patient?.getFullName())
    }

    /**
     * Test za metodu za dodelu imena i prezimena lekaru, za prosledjenom null vrednoscu
     * @throws NullPointerException Ako Test uhvati izuzetak prosao je
     */
    @Test
    fun setFullName_null(){
        assertThrows<NullPointerException>
        {
            patient?.setFullName(null)
        }
    }
    /**
     * Test za metodu za dodelu imena i prezimena pacijentu, za prosledjenim argumentima bez razmaka
     * @throws IllegalArgumentException Ako Test uhvati izuzetak prosao je
     */
    @ParameterizedTest
    @CsvSource(
        "''",
        "PeraPeric"
    )
    fun setImePrezime_nemaRazmaka(fullName: String){
        assertThrows<IllegalArgumentException> {
            patient?.setFullName(fullName)
        }

    }
    /**
     * Test za metodu za dodelu imena i prezimena pacijenta, za prosledjenim
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
            patient?.setFullName(fullName)
        }

    }
    /**
     * Test za metodu za dodelu imena i prezimena pacijentu, za prosledjenim ispravnim argumentima,
     * ako su dodeljeni argumenti jednaki prosledjenim test prolazi
     */
    @ParameterizedTest
    @CsvSource(
        "Pera Peric",
        "Pera Perić",
        "Ivan Ivanović"

    )
    fun setFullName_samoSlova(fullName: String){
        patient?.setFullName(fullName)
        assertEquals(fullName,patient?.getFullName())

    }

    /**
     * Funkcija koja testira funkciju getHospitalId
     */
    @Test
    fun getHosputitalId_test(){
        assertEquals("1",patient?.getHospitalId())
    }

    /**
     * Funkcija koja testira metodu za dodeljivanje null vrednosti hospitalId parametra
     * @throws NullPointerException Test je prosao ako ce baci izuzetak
     */
    @Test
    fun setHospitalId_nullTest(){
        assertThrows<NullPointerException> {
            patient?.setHospitalId(null)
        }
    }
    /**
     * Funkcija koja testira metodu za dodeljivanje praznog stringa hospitalId parametra
     * @throws IllegalArgumentException Ako metoda baci izuzetak test prolazi
     */
    @Test
    fun setHosputalId_prazanTest(){
        assertThrows<IllegalArgumentException> {
            patient?.setHospitalId("")
        }
    }

    /**
     * Funckija koja testira metodu za dodeljivanje ispravne vrednosti hospitalId
     */
    @Test
    fun setHospitalId_ispravanTest(){
        patient?.setHospitalId("2")
        assertEquals("2",patient?.getHospitalId())
    }

    /**
     * Funckija koja testira setJmbg kada je prosledjena null vrednost
     * @throws NullPointerException Ako se baci izuzetak test prolazi
     */
    @Test
    fun setJmbg_test_null(){
        assertThrows<NullPointerException> {
            patient?.setJmbg(null)
        }

    }
    /**
     * Funckija koja testira setJmbg kada je prosledjena prazan string
     * @throws IllegalArgumentException Ako se baci izuzetak test prolazi
     */
    @Test
    fun setJmbg_test_prazan(){
        assertThrows<IllegalArgumentException> {
            patient?.setJmbg("")
        }

    }

    /**
     * Funckija koja testira setJmbg kada je prosledjen string koji ili ne sadrzi samo brojeve
     * ili nema tacno 13 cifara
     * @throws NullPointerException Ako se baci izuzetak test prolazi
     */
    @ParameterizedTest
    @CsvSource(
        "bb111",
        "123456",
        )
    fun setJmbg_test_nisuSamoCifreIliKraci(jmbg: String){
        assertThrows<IllegalArgumentException> {
            patient?.setJmbg(jmbg)
        }
    }

    /**
     * Funkcija koja testira setJmbg kad je prosledjen ispravan argument
     */
    @Test
    fun setJmbg_ispravno(){
        patient?.setJmbg("1007002790023")
        assertEquals("1007002790023",patient?.getJmbg())
    }

    /**
     * Funkcija koja testira toString metodu i ako se sadrze svi stringovi, test prolazi
     */
    @Test
    fun toString_test(){
        val string=patient?.toString()
        assertTrue(string?.contains("1")!!)
        assertTrue(string?.contains("2")!!)
        assertTrue(string?.contains("1")!!)
        assertTrue(string?.contains("1007002790023")!!)

        assertTrue(string?.contains("Pera Peric")!!)
    }
    /**
     * Metoda za test equals metode, tako sto se prosledjuju argumenti, i da ocekivanu true ili false vrenost,
     * zatim se uporedju za rezultatima metode
     */
    @ParameterizedTest
    @CsvSource(
        ("1,2,Pera Peric,1,1007002790023,true"),
        ("2,2,Pera Peric,1,1007002790022,true"),
        ("1,1,Pera Peric,1,1007002790022,true"),
        ("2,3,Pera Peric,1,1007002790023,true"),
        ("2,3,Pera Peric,1,1007002790022,false"),
    )
    fun equals_test(id: String, userId: String, fullName: String, hospitalId: String,jmbg:String, expected: Boolean){
        val patient2 = Patient(id,userId,fullName,hospitalId,jmbg)

        assertEquals(expected, patient?.equals(patient2))
    }

    /**
     * Test za hashCode metodu, sa razlicitim parametrima jer hadhCode zavisi od id,userId, fullName i hospitalId,
     * ako su svi ti argumenti isti, hashCode je isti, ako ne nije
     */
    @ParameterizedTest
    @CsvSource(
        ("1,2,Pera Peric,1,1007002790023,true"),
        ("2,2,Pera Peric,1,1007002790023,false"),
        ("1,1,Pera Peric,1,1007002790023,false"),
        ("2,3,Pera Mitic,1,1007002790023,false"),
        ("2,1,Pera Peric,2,1007002790023,false"),
    )
    fun hashCodeTest(id: String, userId: String, fullName: String, hospitalId: String, jmbg: String, expected: Boolean){

        val patient2 =Patient(id,userId,fullName,hospitalId,jmbg)

        assertEquals(expected,patient.hashCode().equals(patient2.hashCode()))
    }


}