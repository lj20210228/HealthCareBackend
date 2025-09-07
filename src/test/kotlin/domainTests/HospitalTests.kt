package com.example.domainTests

import com.example.domain.Doctor
import com.example.domain.Hospital
import io.ktor.util.network.NetworkAddress
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals
import kotlin.test.assertTrue


/**
 * Klasa koja testira metode [Hospital] klase
 * @property hospital [Hospital] objekat koji se koristi u testovima
 */
class HospitalTests {

    var hospital: Hospital?=null
    /**
     * Pre svakog testa se [hospital] Inicijalizuje na ispravne vrednosti
     */
    @BeforeEach
    fun setUp(){
        hospital= Hospital("1","Opsta bolnica Uzice","Uzice","Milosa Obrenovica 32")

    }

    /**
     * Posle svakog testa se [hospital] Inicijalizuje na null, kako ne bi doslo do curenja memorije
     */
    @AfterEach
    fun tearDown(){
        hospital=null
    }

    /**
     * Funkcija koja testira funkciju getId
     */
    @Test
    fun getIdTest(){
        assertEquals("1",hospital?.getId())

    }
    /**
     * Funkcija koja testira metodu za dodeljivanje null vrednosti id bolnice
     * @throws NullPointerException Test je prosao ako ce baci izuzetak
     */
    @Test
    fun setId_null(){
        assertThrows<NullPointerException> { hospital?.setId(null) }
    }
    /**
     * Funkcija koja testira metodu za dodeljivanje praznog stringa id bolnice
     * @throws IllegalArgumentException Ako metoda baci izuzetak test prolazi
     */
    @Test
    fun setId_prazanString(){
        assertThrows<IllegalArgumentException> { hospital?.setId("") }
    }
    /**
     * Funckija koja testira metodu za dodeljivanje ispravne vrednosti id bolnice,
     * ukoliko je dodeljeno ime jednako vracenom id bolnice test prolazi
     */
    @Test
    fun setId_ispravno(){
        hospital?.setId("2n2p2jj")
        assertEquals("2n2p2jj",hospital?.getId())

    }

    /**
     * Funkcija koja testira metodu za vracanje ispravne vrednosti imena bolnice,
     * i ukoliko je isto kao zadato test prolazi
     */
    @Test
    fun getName(){
        assertEquals("Opsta bolnica Uzice",hospital?.getName())

    }
    /**
     * Funkcija koja testira metodu za dodeljivanje null vrednosti imenu bolnice
     * @throws NullPointerException Test je prosao ako ce baci izuzetak
     */
    @Test
    fun setName_null(){
        assertThrows<NullPointerException> {
            hospital?.setName(null)
        }
    }
    /**
     * Funkcija koja testira metodu za dodeljivanje praznog stringa imenu bolnice
     * @throws IllegalArgumentException Ako metoda baci izuzetak test prolazi
     */
    @Test
    fun setName_prazanString(){
        assertThrows<IllegalArgumentException> {
            hospital?.setName("")
        }
    }

    /**
     * Funkcija koja testira metodu za dodeljivanje imena bolnici, koji prolazi
     * ukoliko su dodeljena imena jednaka vracenim imenima bolnice metodom getName
     */
    @ParameterizedTest
    @CsvSource(
        "Opsta bolnica Vrbas",
        "KBC Zvezdara",
        "Narodni front"
    )
    fun setName_ispravno(name:String){
        hospital?.setName(name)
        assertEquals(name,hospital?.getName())
    }

    /**
     * Funkcija koja testira metodu za vracanje ispravne vrednosti imena grada,
     * i ukoliko je isto kao zadato test prolazi
     */
    @Test
    fun getCity(){
        assertEquals("Uzice",hospital?.getCity())

    }
    /**
     * Funkcija koja testira metodu za dodeljivanje null vrednosti imenu grada
     * @throws NullPointerException Test je prosao ako ce baci izuzetak
     */
    @Test
    fun setCity_null(){
        assertThrows<NullPointerException> {
            hospital?.setCity(null)
        }
    }
    /**
     * Funkcija koja testira metodu za dodeljivanje praznog stringa imenu grada
     * @throws IllegalArgumentException Ako metoda baci izuzetak test prolazi
     */
    @Test
    fun setCity_prazanString(){
        assertThrows<IllegalArgumentException> {
            hospital?.setCity("")
        }
    }

    /**
     * Metoda koja za testira da li funkcija setCity radi ispravno sa prodledjenim neispravnim argumentima
     * @throws IllegalArgumentException Ako string sadrzi brojeve ili specijalne karaktere, ako se baci izuzetak test prolazi
     */
    @ParameterizedTest
    @CsvSource(
        "Beograd 2",
        "Bor@"
    )
    fun setCity_neispravanArgument(city: String){
        assertThrows<IllegalArgumentException> {
            hospital?.setCity(city)
        }


    }
    /**
     * Funkcija koja testira metodu za dodeljivanje imena grada, koji prolazi
     * ukoliko su dodeljena imena jednaka vracenim imenima grada metodom getCity
     */
    @ParameterizedTest
    @CsvSource(
        "Beograd",
        "Novi Sad",
    )
    fun setCity_ispravno(city:String){
        hospital?.setCity(city)
        assertEquals(city,hospital?.getCity())
    }
    /**
     * Funkcija koja testira metodu za vracanje ispravne vrednosti adrese bolnice,
     * i ukoliko je isto kao zadato test prolazi
     */
    @Test
    fun getAdress(){
        assertEquals("Milosa Obrenovica 32",hospital?.getAddress())

    }
    /**
     * Funkcija koja testira metodu za dodeljivanje null vrednosti adresi
     * @throws NullPointerException Test je prosao ako ce baci izuzetak
     */
    @Test
    fun setAddress_null(){
        assertThrows<NullPointerException> {
            hospital?.setAddress(null)
        }
    }
    /**
     * Funkcija koja testira metodu za dodeljivanje praznog stringa adresi
     * @throws NullPointerException Test je prosao ako ce baci izuzetak
     */
    @Test
    fun setAddress_prazanString(){
        assertThrows<IllegalArgumentException> {
            hospital?.setAddress("")
        }
    }
    /**
     * Funkcija koja testira metodu za dodeljivanje argumenta sa neispravnim formatom adresi,
     * to jest ako sadrzi ista sem slova i brojeva
     * @throws NullPointerException Test je prosao ako ce baci izuzetak
     */
    @ParameterizedTest
    @CsvSource(
        "Knjaza Milosa 4/2"
    )
    fun setAddress_neispravanArgument(address: String){
        assertThrows<IllegalArgumentException> {
            hospital?.setAddress(address)
        }


    }
    /**
     * Funkcija koja testira metodu za dodeljivanje adrese, koji prolazi
     * ukoliko su dodeljena imena jednaka vracenim adresama metodom getAddress
     */
    @ParameterizedTest
    @CsvSource(
        "Ustanicka 11",
        "Kneza Milosa 10",
    )
    fun setAddress_ispravno(address:String){
        hospital?.setAddress(address)
        assertEquals(address,hospital?.getAddress())
    }

    /**
     * Parametrizovani test za proveru ispravnosti implementacije metode [Hospital.equals].
     *
     * Test koristi [CsvSource] kako bi pokrio više slučajeva:
     * 1. Kada je `id` isti → objekti se smatraju jednakim bez obzira na ostale atribute.
     * 2. Kada su `name`, `city` i `address` isti, a `id` različit → objekti se takođe smatraju jednakim.
     * 3. Kada se razlikuje `name` → objekti se smatraju različitim.
     * 4. Kada se razlikuje `city` → objekti se smatraju različitim.
     * 5. Kada se razlikuje `address` → objekti se smatraju različitim.
     *
     * @param id Id druge bolnice
     * @param name Ime druge bolnice
     * @param city Grad druge bolnice
     * @param address Adresa druge bolnice
     * @param expected Očekivani rezultat poređenja (`true` ako objekti treba da budu jednaki, `false` inače)
     */
    @ParameterizedTest
    @CsvSource(
        // isti id -> equals = true
        "1,Druga bolnica,Beograd,Nemanjina 4,true",
        // sve isto osim id -> equals = true (jer se poklapaju name, city i address)
        "2,Opsta bolnica Uzice,Uzice,Milosa Obrenovica 32,true",
        // razlika u imenu -> equals = false
        "2, bolnica Uzice,Uzice,Milosa Obrenovica 32,false",
        // razlika u gradu -> equals = false
        "2,Opsta bolnica Uzice,Cacak,Milosa Obrenovica 32,false",
        // razlika u adresi -> equals = false
        "2,Opsta bolnica Uzice,Uzice,Milosa Obrenovica 34,false"
    )
    fun equals_TEST(id: String, name: String, city: String, address: String, expected: Boolean) {
        // original hospital
        val hospital1 = Hospital("1", "Opsta bolnica Uzice", "Uzice", "Milosa Obrenovica 32")
        val hospital2 = Hospital(id, name, city, address)

        assertEquals(expected, hospital1 == hospital2)
    }
    /**
     * Test za proveru ispravnosti implementacije metode [Hospital.hashCode].
     *
     * Pravilo je da ukoliko su dva objekta jednaka prema [Hospital.equals],
     * njihov [hashCode] mora biti identičan.
     *
     * U ovom testu se kreira novi objekat sa istim vrednostima kao i postojeći `hospital`,
     * i proverava se da li oba objekta vraćaju isti hash kod.
     */

    @Test
    fun hashCode_test(){
        val hospital2=Hospital("1","Opsta bolnica Uzice","Uzice","Milosa Obrenovica 32")

        assertEquals(hospital.hashCode(),hospital2.hashCode())
    }

    /**
     * Test za proveru ispravnosti implementacije metode [Hospital.toString].
     *
     * Metoda [Hospital.toString] treba da vraća string reprezentaciju objekta
     * koja uključuje sve njegove atribute: `id`, `name`, `city` i `address`.
     *
     * U ovom testu se poziva `toString()` nad objektom `hospital` i proverava se
     * da li vraćeni string sadrži sve vrednosti atributa koje su bile postavljene u objektu.
     */
    @Test
    fun toString_test(){
        val string=hospital.toString()
        assertTrue(string.contains("1"))
        assertTrue(string.contains("Opsta bolnica Uzice"))
        assertTrue(string.contains("Uzice"))
        assertTrue(string.contains("Milosa Obrenovica 32"))



    }
}