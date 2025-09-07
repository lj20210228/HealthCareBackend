package com.example.service

import com.example.domain.Hospital
import com.example.service.hospital.HospitalServiceImplementation
import com.example.service.hospital.HospitalServiceInterface
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.io.File
import kotlin.test.assertEquals

/**
 * Test klasa za interfejs [HospitalServiceInterface]
 * @author Lazar Janković
 * @see HospitalServiceInterface
 * @see Hospital
 * @property hospitalService Instanca interfejsa [HospitalServiceInterface]
 * @property file Fajl koji se simulirati bazu u testovima
 */
class HospitalServiceInterfaceTest {
     var hospitalService: HospitalServiceInterface?=null
    val file= File("hospitals.json")

    /**
     * Inicijalizacija [hospitalService] i [file] na pocetne vrednosti pre svakog testa
     */
    @BeforeEach
    fun setUp(){
        hospitalService= HospitalServiceImplementation()
        file.writeText("")
    }
    /**
     * Inicijalizacija [hospitalService] i [file] na pocetne vrednosti posle svakog testa
     */
    @AfterEach
    fun tearDown(){
        file.writeText("")

        hospitalService=null
    }

    /**
     * Metoda za dodavanje nove bolnice kada je prosledjeni parametar null,
     * ako se baci [NullPointerException] test prolazi
     */
    @Test
    fun addHospitalTest_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                hospitalService?.addHospital(null)
            }
        }

    }

    /**
     * Test metode za dodavanje bolnice kada ona vec postoji,
     * ako se baci [IllegalArgumentException] test prolazi
     * @see Hospital.equals
     */
    @ParameterizedTest
    @CsvSource(
        "1,Opsta Bolnica Cacak,Cacak,Kneza Milosa 44",
        "1,Opsta Bolnica Sabac,Sabac,Kneza Milosa 44"
        )
    fun addHospitalTest_vecPostoji(
        hospitalId:String,name: String,city: String,address: String
    ){
        runBlocking {
            val hospital= Hospital(hospitalId,name,city,address)
            val result=hospitalService?.addHospital(hospital)
            assertEquals(result,hospital)
            assertThrows<IllegalArgumentException> {
                hospitalService?.addHospital(hospital)
            }
        }

    }

    /**
     * Metoda koja testira uspesno dodavanje [Hospital] objekta,
     * ako je povratna vrednost metode addHospital jednaka prosledjenom parametru test prolazi
     */
    @Test
    fun addHospitalTest_uspesno(){
        val hospital= Hospital("1","Opsta bolnica Uzice","Uzice","Milosa Obrenovica 12")
        runBlocking {
            val result=hospitalService?.addHospital(hospital)
            assertEquals(result,hospital)
        }
    }

    /**
     * Test za pronalazenje bolnice po id, kada je parametar null,
     * ako se baci [NullPointerException] test prolazi
     */
    @Test
    fun getHospitalByIdTest_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                hospitalService?.getHospitalById(null)
            }
        }
    }
    /**
     * Test za pronalazenje bolnice po id, kada je parametar prazan string,
     * ako se baci [IllegalArgumentException] test prolazi
     */
    @Test
    fun getHospitalByIdTest_prazanString(){
        runBlocking {
            assertThrows<IllegalArgumentException> {
                hospitalService?.getHospitalById("")
            }
        }
    }
    /**
     * Test za pronalazenje bolnice po id, kada je parametar ispravan,
     * ali ne postoji bolnica sa tim id, vraca se null
     */
    @Test
    fun getHospitalByIdTest_bolnicaNePostoji(){
        runBlocking {
            assertEquals(null,hospitalService?.getHospitalByName("Opsta bolnica Kragujevac"))
        }
    }

    /**
     * Metoda za pronalazenje bolnice po id, ako je bolnica vracena metodom getHospitalById
     * jednaka dodatoj test prolazi
     */
    @Test
    fun getHospitalByIdTest_bolnicaPostoji(){
        val hospital= Hospital("1","Opsta bolnica Uzice","Uzice","Milosa Obrenovica 12")

        runBlocking {
            val result=hospitalService?.addHospital(hospital)
            assertEquals(result,hospitalService?.getHospitalById("1"))
        }
    }
    /**
     * Test za pronalazenje bolnice po imenu , kada je parametar null,
     * ako se baci [NullPointerException] test prolazi
     */
    @Test
    fun getHospitalByNameTest_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                hospitalService?.getHospitalByName(null)
            }
        }
    }
    /**
     * Test za pronalazenje bolnice po imenu, kada je parametar prazan string,
     * ako se baci [IllegalArgumentException] test prolazi
     */
    @Test
    fun getHospitalByNameTest_prazanString(){
        runBlocking {
            assertThrows<IllegalArgumentException> {
                hospitalService?.getHospitalByName("")
            }
        }
    }
    /**
     * Test za pronalazenje bolnice po imenu, kada je parametar ispravan,
     * ali ne postoji bolnica sa tim imenom, vraca se null
     */
    @Test
    fun getHospitalByNameTest_bolnicaNePostoji(){
        runBlocking {
            assertEquals(null,hospitalService?.getHospitalByName("Sabac"))
        }
    }
    /**
     * Metoda za pronalazenje bolnice po imenu, ako je bolnica vracena metodom getHospitalByName
     * jednaka dodatoj test prolazi
     */
    @Test
    fun getHospitalByNameTest_bolnicaPostoji(){
        val hospital= Hospital("1","Opsta bolnica Uzice","Uzice","Milosa Obrenovica 12")

        runBlocking {
            val result=hospitalService?.addHospital(hospital)
            assertEquals(result,hospitalService?.getHospitalByName("Opsta bolnica Uzice"))
        }
    }
    /**
     * Test za pronalazenje bolnica po imenu grada , kada je parametar null,
     * ako se baci [NullPointerException] test prolazi
     */
    @Test
    fun getHospitalsInCityTest_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                hospitalService?.getHospitalsInCity(null)
            }
        }
    }
    /**
     * Test za pronalazenje bolnica po imenu grada, kada je parametar prazan string,
     * ako se baci [IllegalArgumentException] test prolazi
     */
    @Test
    fun getHospitalsInCityTest_prazanString(){
        runBlocking {
            assertThrows<IllegalArgumentException> {
                hospitalService?.getHospitalsInCity("")
            }
        }
    }
    /**
     * Test za pronalazenje bolnice po imenu grada, kada je parametar ispravan,
     * ali ne postoje bolnice u tom gradu, vraca se prazna lista
     */
    @Test
    fun getHospitalsInCityTest_bolnicaNePostoji(){
        runBlocking {
            assertEquals(null,hospitalService?.getHospitalsInCity("Sabac"))
        }
    }
    /**
     * Metoda za pronalazenje bolnica po imenu grada, ako je lista  bolnica vracena metodom getHospitalsInCity
     * jednaka dodatoj test prolazi
     */
    @Test
    fun getHospitalsInCityTest_bolnicaPostoji(){
        val hospital= Hospital("1","Opsta bolnica Uzice","Uzice","Milosa Obrenovica 12")
        val hospital2= Hospital("2","Dom Zdravlja Uzice Uzice","Uzice","Ustanicka 2")

        runBlocking {
            hospitalService?.addHospital(hospital)
            hospitalService?.addHospital(hospital2)
            val list=listOf(hospital,hospital2)


            assertEquals(list,hospitalService?.getHospitalsInCity("Uzice"))
        }
    }

    /**
     * Test metoda za vracanje svih bolnica kada ih nema, ako je lista vracenih bolnica prazna, test prolazi
     */
    @Test
    fun getAllHospitals_nemaBolnica(){


        runBlocking {
            assertEquals(emptyList(),hospitalService?.getAllHospitals())
        }
    }

    /**
     * Test metoda za pronalazak svih bolnica kada ih ima, ako je lista dodatih bolnica jednaka
     * vracenoj metodom getAllHospitals test prolazi
     */
    @Test
    fun getAllHospitals_bolnicaPostoji(){
        val hospital= Hospital("1","Opsta bolnica Uzice","Uzice","Milosa Obrenovica 12")
        val hospital2= Hospital("2","Dom Zdravlja Uzice Uzice","Uzice","Ustanicka 2")

        runBlocking {
            hospitalService?.addHospital(hospital)
            hospitalService?.addHospital(hospital2)
            val list=listOf(hospital,hospital2)


            assertEquals(list,hospitalService?.getAllHospitals())
        }
    }
}