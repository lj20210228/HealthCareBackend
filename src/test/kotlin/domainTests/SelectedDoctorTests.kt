package com.example.domainTests

import com.example.domain.SelectedDoctor
import io.ktor.server.sessions.Sessions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Test klasa za proveru funkcionalnosti klase [SelectedDoctor].
 *
 * Ova klasa testira sve javne metode koje klasa [SelectedDoctor] nudi:
 * - Getter i setter metode za [SelectedDoctor.patientId] i [SelectedDoctor.doctorId]
 * - Validaciju parametara (null vrednosti i prazan string)
 * - Implementacije metoda [SelectedDoctor.equals], [SelectedDoctor.hashCode] i [SelectedDoctor.toString]
 *
 * Testovi koriste JUnit 5 anotacije:
 * - [BeforeEach] za inicijalizaciju objekta pre svakog testa
 * - [AfterEach] za čišćenje nakon svakog testa
 * - [ParameterizedTest] za parametarski test equals metode
 *
 * @see SelectedDoctor
 */
class SelectedDoctorTests {

    var selectedDoctor: SelectedDoctor?=null

    /**
     * Inicijalizuje [selectedDoctor] objektom pre svakog testa.
     */
    @BeforeEach
    fun setUp(){
        selectedDoctor= SelectedDoctor("1","1")

    }
    /**
     * Postavlja [selectedDoctor] na null nakon svakog testa.
     */
    @AfterEach
    fun tearDown(){
        selectedDoctor=null
    }
    /**
     * Testira da metoda [SelectedDoctor.getPatientId] ispravno vraća vrednost patientId.
     */
    @Test
    fun getPatientIdTest(){
        assertEquals("1",selectedDoctor?.getPatientId())
    }

    /**
     * Testira da metoda [SelectedDoctor.getDoctorId] ispravno vraća vrednost doctorId.
     */
    @Test
    fun getDoctorIdTest(){
        assertEquals("1",selectedDoctor?.getDoctorId())
    }
    /**
     * Testira da metoda [SelectedDoctor.setPatientId] baca [NullPointerException]
     * kada se prosledi null vrednost.
     */
    @Test
    fun setPatientId_null(){
        assertThrows<NullPointerException> {
            selectedDoctor?.setPatientId(null)
        }
    }
    /**
     * Testira da metoda [SelectedDoctor.setPatientId] baca [IllegalArgumentException]
     * kada se prosledi prazan string.
     */
    @Test
    fun setPatientId_prazanString(){
        assertThrows<IllegalArgumentException> {
            selectedDoctor?.setPatientId("")
        }
    }
    /**
     * Testira da metoda [SelectedDoctor.setPatientId] ispravno menja vrednost patientId
     * kada se prosledi validna vrednost.
     */
    @Test
    fun setPatientId_ispravno() {
        selectedDoctor?.setPatientId("2")
        assertEquals("2", selectedDoctor?.getPatientId())
    }

    /**
     * Testira da metoda [SelectedDoctor.setDoctorId] baca [NullPointerException]
     * kada se prosledi null vrednost.
     */
    @Test
    fun setDoctorId_null() {
        assertThrows<NullPointerException> {
            selectedDoctor?.setDoctorId(null)
        }
    }

    /**
     * Testira da metoda [SelectedDoctor.setDoctorId] baca [IllegalArgumentException]
     * kada se prosledi prazan string.
     */
    @Test
    fun setDoctorId_prazanString() {
        assertThrows<IllegalArgumentException> {
            selectedDoctor?.setDoctorId("")
        }
    }

    /**
     * Testira da metoda [SelectedDoctor.setDoctorId] ispravno menja vrednost doctorId
     * kada se prosledi validna vrednost.
     */
    @Test
    fun setDoctorId_ispravno() {
        selectedDoctor?.setDoctorId("2")
        assertEquals("2", selectedDoctor?.getDoctorId())
    }

    /**
     * Parametarski test za metodu [SelectedDoctor.equals].
     *
     * Testira da li su dva objekta [SelectedDoctor] jednaka u zavisnosti od kombinacije
     * prosleđenih parametara [id1] i [id2].
     *
     * @param id1 vrednost patientId za drugi objekat
     * @param id2 vrednost doctorId za drugi objekat
     * @param boolean očekivani rezultat poređenja
     */
    @ParameterizedTest
    @CsvSource(
        "1,1,true",
        "1,2,false",
        "2,1,false"
    )
    fun equalsTest(id1: String, id2: String, boolean: Boolean) {
        val doctor2 = SelectedDoctor(id1, id2)
        assertEquals(boolean, selectedDoctor?.equals(doctor2))
    }

    /**
     * Testira da metoda [SelectedDoctor.hashCode] vraća isti rezultat
     * za dva objekta sa istim vrednostima polja.
     */
    @Test
    fun hashCode_test() {
        val doctor2 = SelectedDoctor("1", "1")
        assertEquals(selectedDoctor?.hashCode(), doctor2.hashCode())
    }

    /**
     * Testira da metoda [SelectedDoctor.toString] vraća string koji sadrži
     * vrednosti polja patientId i doctorId.
     */
    @Test
    fun toString_test() {
        val doctor2 = selectedDoctor.toString()
        assertTrue(doctor2.contains("1"))
        assertTrue(doctor2.contains("1"))
    }
}