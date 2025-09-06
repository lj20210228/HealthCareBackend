package com.example.service

import com.example.domain.Doctor
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Test klasa za interfejs [DoctorServiceInterface]
 * @author Lazar Janković
 * @see DoctorServiceInterface
 * @property doctorService Parametar koji predstavlja instancu [DoctorServiceInterface]
 * @property file Test fajl koji simulira tabelu u bazi
 */
abstract class DoctorServiceInterfaceTest {

    /**
     * Funckija koja vraca instancu interfejsa,
     * to jest klasu koja ga nasledjuje
     */
    abstract fun getInstance(): DoctorServiceInterface

    var doctorService: DoctorServiceInterface?=null
    val file=File("doctors.json")

    /**
     * Funkcija koja se izvrsava pre svakog testa, inicijalizacija [doctorService]
     */
    @BeforeEach
    fun setUp(){
        doctorService=getInstance()
    }

    /**
     * Funckija koja se izvrsava posle svake metode,
     * inicijalizacija [doctorService] na null, i prazni [file]
     */
    @AfterEach
    fun tearDown(){
        doctorService=null
        file.writeText("")

    }

    /**
     * Test za funkciju addDoctor, kad je prosledjeni parametar null,
     * @throws NullPointerException prolazi ako se baci izuzetal
     */
    @Test
     fun addDoctorTest_null(){
         runBlocking {
             assertThrows<NullPointerException> {
                 doctorService?.addDoctor(null)
             }
         }
    }

    /**
     * Test za funkciju addDoctor kad lekar vec postoji
     * @throws IllegalArgumentException Ako se baci izuzetak test prolazi
     */
    @Test
    fun addDoctorTest_vec_postoji(){
        val doctor1= Doctor("5","5","Pera Peric","Neurolog",20,0,"1",false)
        val doctor2= Doctor("5","5","Pera Peric","Neurolog",20,0,"1",false)

        runBlocking {
            doctorService?.addDoctor(doctor1)
            assertThrows<IllegalArgumentException> {
                doctorService?.addDoctor(doctor2)
            }
        }
    }

    /**
     * Test za metodu addDoctor
     * @return [Doctor] Ako funkcija uspesno vrati lekara koji je dodat, test prolazi
     */
    @Test
    fun addDoctorTest_uspesno(){
        val doctor= Doctor("1","1","Pera Peric","Neurolog",20,0,"1",false)

        runBlocking {
            val added=doctorService?.addDoctor(doctor)
            assertEquals(added,doctorService?.getDoctorForId("1"))
        }
    }

    /**
     * Funckija koja testira metodu getDoctorForId gde je prosledjena null vrednost
     * @throws NullPointerException Ako metoda baci izuzetak test prolazi
     */
    @Test
    fun getDoctorForIdTest_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                doctorService?.getDoctorForId(null)
            }
        }
    }
    /**
     * Funckija koja testira metodu getDoctorForId gde je prosledjen prazan string
     * @throws IllegalArgumentException Ako metoda baci izuzetak test prolazi
     */
    @Test
    fun getDoctorForIdTest_PrazanString(){
        runBlocking {
            assertThrows<IllegalArgumentException> {
                doctorService?.getDoctorForId("")
            }
        }
    }
    /**
     * Funckija koja testira metodu getDoctorForId gde je prosledjen ispravan id,
     * ali lekar ne postoji u bazi
     * @return [null] Ako se vrati null vresnost test prolazi
     */
    @Test
    fun getDoctorForIdTest_ispravanId_ne_postoji(){
        runBlocking {

            assertEquals(null,doctorService?.getDoctorForId("7"))

        }
    }

    /**
     * Funckija koja testira metodu getDoctorForId gde je prosledjen ispravan id
     * @return Ako se vrati lekar sa zadatim id test prolazi
     */
    @Test
    fun getDoctorForIdTest_ispravanId(){
        runBlocking {
            val doctor= Doctor("7","7","Pera Peric","Neurolog",20,0,"1",false)
            doctorService?.addDoctor(doctor)
            assertEquals(doctor,doctorService?.getDoctorForId("7"))

        }
    }
    /**
     * Funckija koja testira metodu getAllDoctorsInHospital gde je prosledjena null vrednost
     * @throws NullPointerException Ako metoda baci izuzetak test prolazi
     */
    @Test
    fun getAllDoctorsInHospitalTest_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                doctorService?.getAllDoctorsInHospital(null)
            }
        }
    }
    /**
     * Funckija koja testira metodu getAllDoctorsInHospital gde je prosledjen prazan string
     * @throws IllegalArgumentException Ako metoda baci izuzetak test prolazi
     */
    @Test
    fun getAllDoctorsInHospitalTest_PrazanString(){
        runBlocking {
            assertThrows<IllegalArgumentException> {
                doctorService?.getDoctorForId("")
            }
        }
    }
    /**
     * Funckija koja testira metodu getAllDoctorsInHospital gde je prosledjena ispravna vrednost,
     * ali ne postoji taj lekar u bazi
     * @return [emptyList] Ako metoda vrati praznu listu test prolazi
     */
    @Test
    fun getAllDoctorsInHospital_praznaLista(){
        runBlocking {
            assertEquals(emptyList(),doctorService?.getAllDoctorsInHospital("1"))
        }
    }

    /**
     * Funckija koja testira metodu getAllDoctorsInHospital gde je prosledjena ispravna vrednost,
     * @return [List[Doctor]] Ako metoda vrati listu lekara koja je jednaka lokalnoj,
     *  gde su dodati isti lekari kao i u listi servisa, test prolazi
     */
    @Test
    fun getAllDoctorsInHospitalTest_ispravanId(){
        runBlocking {
            val doctor1= Doctor("7","7","Pera Peric","Neurolog",20,0,"1",false)
            val doctor2= Doctor("1","1","Pera Mikic","Dermatolog",20,0,"1",false)
            val doctor3= Doctor("2","2","Jovan Jovic","Lekar opste prakse",20,0,"1",false)


            doctorService?.addDoctor(doctor1)
            doctorService?.addDoctor(doctor2)
            doctorService?.addDoctor(doctor3)
            val doctorList=mutableListOf<Doctor>()
            doctorList.add(doctor1)
            doctorList.add(doctor2)
            doctorList.add(doctor3)

            assertEquals(doctorList,doctorService?.getAllDoctorsInHospital("1"))

        }
    }
    /**
     * Funckija koja testira metodu getAllGeneralDoctorsInHospital gde je prosledjena null vrednost
     * @throws NullPointerException Ako metoda baci izuzetak test prolazi
     */
    @Test
    fun getAllGeneralDoctorsInHospitalTest_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                doctorService?.getAllGeneralDoctorsInHospital(null)
            }
        }
    }
    /**
     * Funckija koja testira metodu getAllGeneralDoctorsInHospital gde je prosledjen prazan string
     * @throws IllegalArgumentException Ako metoda baci izuzetak test prolazi
     */
    @Test
    fun getAllGeneralDoctorInHospitalTest_PrazanString(){
        runBlocking {
            assertThrows<IllegalArgumentException> {
                doctorService?.getAllGeneralDoctorsInHospital("")
            }
        }
    }
    /**
     * Funckija koja testira metodu getAllGeneralDoctorsInHospital gde je prosledjena ispravna vrednost,
     * ali ne postoji taj lekar u bazi
     * @return [emptyList] Ako metoda vrati praznu listu test prolazi
     */
    @Test
    fun getAllGeneralDoctorsInHospital_praznaLista(){
        runBlocking {
            assertEquals(emptyList(),doctorService?.getAllGeneralDoctorsInHospital("1"))
        }
    }

    /**
     * Funckija koja testira metodu getAllGeneralDoctorsInHospital gde je prosledjena ispravna vrednost,
     * @return [List[Doctor]] Ako metoda vrati listu lekara koja je jednaka lokalnoj,
     *  gde su dodati isti lekari kao i u listi servisa, test prolazi
     */
    @Test
    fun getAllGeneralDoctorsInHospitalTest_ispravanId(){
        runBlocking {
            val doctor1= Doctor("7","7","Pera Peric","Neurolog",20,0,"1",false)
            val doctor2= Doctor("1","1","Pera Mikic","Dermatolog",20,0,"1",false)
            val doctor3= Doctor("2","2","Jovan Jovic","Lekar opste prakse",20,0,"1",true)

           doctorService?.addDoctor(doctor1)
           doctorService?.addDoctor(doctor2)
           doctorService?.addDoctor(doctor3)
            val doctorList=listOf(doctor3)

            assertEquals(doctorList,doctorService?.getAllGeneralDoctorsInHospital("1"))

        }
    }
    /**
     * Funckija koja testira metodu getAllGeneralDoctorsInHospitalWithoutMaxPatients gde je prosledjena null vrednost
     * @throws NullPointerException Ako metoda baci izuzetak test prolazi
     */
    @Test
    fun getAllGeneralDoctorsInHospitalWithoutMaxPatientsTest_null(){
        runBlocking {
            assertThrows<NullPointerException> {
                doctorService?.getAllGeneralDoctorsInHospitalWithoutMaxPatients(null)
            }
        }
    }
    /**
     * Funckija koja testira metodu getAllGeneralDoctorsInHospitalWithoutMaxPatients gde je prosledjen prazan string
     * @throws IllegalArgumentException Ako metoda baci izuzetak test prolazi
     */
    @Test
    fun getAllGeneralDoctorInHospitalWithoutMaxPatients_PrazanString(){
        runBlocking {
            assertThrows<IllegalArgumentException> {
                doctorService?.getAllGeneralDoctorsInHospitalWithoutMaxPatients("")
            }
        }
    }
    /**
     * Funckija koja testira metodu getAllGeneralDoctorsInHospitalWithoutMaxPatients gde je prosledjena ispravna vrednost,
     * ali ne postoji taj lekar u bazi
     * @return [emptyList] Ako metoda vrati praznu listu test prolazi
     */
    @Test
    fun getAllGeneralDoctorsInHospitalWithoutMaxPatients_praznaLista(){
        runBlocking {
            assertEquals(emptyList(),doctorService?.getAllGeneralDoctorsInHospitalWithoutMaxPatients("1"))
        }
    }

    /**
     * Funckija koja testira metodu getAllGeneralDoctorsInHospitalWithoutMaxPatients gde je prosledjena ispravna vrednost,
     * @return [List[Doctor]] Ako metoda vrati listu lekara koja je jednaka lokalnoj,
     *  gde su dodati isti lekari kao i u listi servisa, test prolazi
     */
    @Test
    fun getAllGeneralDoctorsInHospitalWithoutMaxPatientsTest_ispravanId(){
        runBlocking {
            val doctor1= Doctor("7","7","Pera Peric","Lekar opste prakse",20,20,"1",true)
            val doctor2= Doctor("1","1","Pera Mikic","Lekar opste prakse",20,0,"1",true)
            val doctor3= Doctor("2","2","Jovan Jovic","Lekar opste prakse",20,0,"1",true)

            doctorService?.addDoctor(doctor1)

            doctorService?.addDoctor(doctor2)
            doctorService?.addDoctor(doctor3)
            val doctorList=listOf(doctor2,doctor3)

            assertEquals(doctorList,doctorService?.getAllGeneralDoctorsInHospitalWithoutMaxPatients("1"))

        }
    }

    /**
     * Funckija koja testira metodu getAllDoctorsInHospitalWithoutMaxPatientsForSpecialization,
     * gde su  prosledjene null vrednosti za hospitalId ili specialization
     * @throws NullPointerException Ako metoda baci izuzetak test prolazi
     */
    @ParameterizedTest
    @CsvSource(
        "null,null",
        "1,null",
        "null,1"
    )
    fun getAllDoctorsInHospitalForSpecializationTest_null(hospitalId: String,specialization:String){
        val realHospitalId = if (hospitalId == "null") null else hospitalId
        val realSpecialization = if (specialization == "null") null else specialization
        runBlocking {
            assertThrows<NullPointerException> {
                doctorService?.getAllDoctorInHospitalForSpecialization(realHospitalId,realSpecialization)
            }
        }
    }
    /**
     * Funckija koja testira metodu getAllDoctorsInHospitalWithoutMaxPatientsForSpecialization,
     * gde su  prosledjeni prazni stringovi za hospitalId ili specialization
     * @throws IllegalArgumentException Ako metoda baci izuzetak test prolazi
     */
    @ParameterizedTest
    @CsvSource(
        "'',''",
        "1,''",
        "'',1"
    )
    fun getAllDoctorsInHospitalWithoutMaxPatientsForSpecialization_prazanString(hospitalId: String,specialization:String){
        runBlocking {
            assertThrows<IllegalArgumentException> {
                doctorService?.getAllDoctorInHospitalForSpecialization(hospitalId,specialization)
            }
        }
    }


    /**
     * Funckija koja testira metodu getAllDoctorsInHospitalWithoutMaxPatientsForSpecialization,
     * gde je prosledjena ispravna vrednost,
     * ali ne postoji taj lekar u bazi
     * @return [emptyList] Ako metoda vrati praznu listu test prolazi
     */
    @Test
    fun getAllDoctorsInHospitalWithoutMaxPatientsForSpecialization_praznaLista(){
        runBlocking {
            assertEquals(emptyList(),doctorService?.getAllDoctorInHospitalForSpecialization("1","Neurolog"))
        }
    }
    /**
     * Funckija koja testira metodu getAllDoctorsInHospitalWithoutMaxPatientsForSpecialization gde su prosledjene ispravne vrednosti,
     * @return [List[Doctor]] Ako metoda vrati listu lekara koja je jednaka lokalnoj,
     * gde su dodati isti lekari kao i u listi servisa, test prolazi
     */
    @Test
    fun getAllDoctorsInHospitalWithoutMaxPatientsForSpecialization_ispravavniId(){
        runBlocking {
            val doctor1= Doctor("7","7","Pera Peric","Dermatolog",20,20,"1",false)
            val doctor2= Doctor("1","1","Pera Mikic","Dermatolog",20,0,"1",false)
            val doctor3= Doctor("2","2","Jovan Jovic","Lekar opste prakse",20,0,"1",true)

            doctorService?.addDoctor(doctor1)
            doctorService?.addDoctor(doctor2)
            doctorService?.addDoctor(doctor3)
            val doctorList=listOf(doctor2)

            assertEquals(doctorList,doctorService?.getAllDoctorsInHospitalWithoutMaxPatientsForSpecialization("1","Dermatolog"))

        }
    }
    /**
     * Funckija koja testira metodu getAllDoctorsInHospitalForSpecialization,
     * gde su  prosledjene null vrednosti za hospitalId ili specialization
     * @throws NullPointerException Ako metoda baci izuzetak test prolazi
     */
    @ParameterizedTest
    @CsvSource(
        "null,null",
        "1,null",
        "null,1"
    )
    fun getAllDoctorsInHospitalWithoutMaxPatientsForSpecializationTest_null(hospitalId: String,specialization:String){
        val realHospitalId = if (hospitalId == "null") null else hospitalId
        val realSpecialization = if (specialization == "null") null else specialization
        runBlocking {
            assertThrows<NullPointerException> {
                doctorService?.getAllDoctorsInHospitalWithoutMaxPatientsForSpecialization(realHospitalId,realSpecialization)
            }
        }
    }
    /**
     * Funckija koja testira metodu getAllDoctorsInHospitalForSpecialization,
     * gde su  prosledjeni prazni stringovi za hospitalId ili specialization
     * @throws IllegalArgumentException Ako metoda baci izuzetak test prolazi
     */
    @ParameterizedTest
    @CsvSource(
        "'',''",
        "1,''",
        "'',1"
    )
    fun getAllDoctorsInHospitalForSpecializationTest_prazanString(hospitalId: String,specialization:String){
        runBlocking {
            assertThrows<IllegalArgumentException> {
                doctorService?.getAllDoctorInHospitalForSpecialization(hospitalId,specialization)
            }
        }
    }


    /**
     * Funckija koja testira metodu getAllDoctorsInHospitalForSpecialization,
     * gde je prosledjena ispravna vrednost,
     * ali ne postoji taj lekar u bazi
     * @return [emptyList] Ako metoda vrati praznu listu test prolazi
     */
    @Test
    fun getDoctorsInHospitalForSpecialization_praznaLista(){
        runBlocking {
            assertEquals(emptyList(),doctorService?.getAllDoctorInHospitalForSpecialization("1","Neurolog"))
        }
    }
    /**
     * Funckija koja testira metodu getAllGeneralDoctorsInHospital gde su prosledjene ispravne vrednosti,
     * @return [List[Doctor]] Ako metoda vrati listu lekara koja je jednaka lokalnoj,
     * gde su dodati isti lekari kao i u listi servisa, test prolazi
     */
    @Test
    fun getDoctorsInHospitalForSpecializationTest_ispravavniId(){
        runBlocking {
            val doctor1= Doctor("7","7","Pera Peric","Neurolog",20,0,"1",false)
            val doctor2= Doctor("1","1","Pera Mikic","Dermatolog",20,0,"1",false)
            val doctor3= Doctor("2","2","Jovan Jovic","Lekar opste prakse",20,0,"1",true)

            doctorService?.addDoctor(doctor1)
            doctorService?.addDoctor(doctor2)
            doctorService?.addDoctor(doctor3)
            val doctorList=listOf(doctor1)

            assertEquals(doctorList,doctorService?.getAllDoctorInHospitalForSpecialization("1","Neurolog"))

        }
    }


    @Test
    fun editCurrentPatientsTest_null(){
       runBlocking {
           assertThrows<NullPointerException> {

               doctorService?.editCurrentPatients(null)
           }
       }

    }
    @Test
    fun editCurrentPatientsTest_prazanString(){
        runBlocking {
            assertThrows<IllegalArgumentException> {

                doctorService?.editCurrentPatients("")
            }
        }

    }
    @Test
    fun editCurrentPatientsTest_ispravno(){
        runBlocking {
            val doctor= Doctor("5","5","Pera Peric","Neurolog",20,4,"1",false)
            doctorService?.addDoctor(doctor)
            assertTrue(doctorService?.editCurrentPatients("5")!!)

            assertEquals(5,doctorService?.getDoctorForId("5")?.getCurrentPatients())
        }

    }

}