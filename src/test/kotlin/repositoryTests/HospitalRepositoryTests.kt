package com.example.repositoryTests

import com.example.database.DatabaseFactory
import com.example.domain.Hospital
import com.example.repository.hospital.HospitalRepository
import com.example.repository.hospital.HospitalRepositoryImplementation
import com.example.response.BaseResponse
import com.example.response.ListResponse
import com.example.service.hospital.HospitalServiceImplementation
import com.example.service.hospital.HospitalServiceInterface
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Test klasa za [HospitalRepository]
 *@see HospitalRepository
 * @see HospitalServiceInterface
 * @see Hospital
 * @see DatabaseFactory
 * @author Lazar Jankovic
 */
class HospitalRepositoryTests {
    private lateinit var hospital: Hospital
    private lateinit var hospitalRepository: HospitalRepository
    private lateinit var service: HospitalServiceInterface

    /**
     * Postavljanje vrednosti propertija na inicajlne vrenosti,
     * i brisanje svih podataka iz hospital tabele
     */
    @BeforeEach
    fun setUp(){

        service= HospitalServiceImplementation()
        hospital= Hospital(
            name = "Opsta Bolnica Pozega",
            city = "Pozega",
            address ="Kneza Milosa 2"
        )

        hospitalRepository= HospitalRepositoryImplementation(service)
        DatabaseFactory.init()
        DatabaseFactory.clearTable("hospital")

    }

    /**
     * Brisanje podataka iz hospital tabele
     */
    @AfterEach
    fun tearDown(){
        DatabaseFactory.clearTable("hospital")

    }

    /**
     * Test za dodavanje bolnice kada je prosledjen null parametar
     */
    @Test
    fun addHospital_nullTest()= runBlocking {
        val hospital=hospitalRepository.addHospital(null)
        assertTrue(hospital is BaseResponse.ErrorResponse)
        assertEquals("Nisu prosledjeni podaci o bolnici",hospital.message)
    }
    /**
     * Test za dodavanje bolnice kada vec  postoji
     */
    @Test
    fun addHospital_vecPostoji()= runBlocking {
        service.addHospital(hospital)
        val hospital=hospitalRepository.addHospital(hospital)
        assertTrue(hospital is BaseResponse.ErrorResponse)
        assertEquals("Bolnica vec postoji",hospital.message)
    }
    /**
     * Test za dodavanje bolnice kada ne postoji
     */
    @Test
    fun addHospital_uspesnoDodavanje()=runBlocking {
        val hospitalReturn=hospitalRepository.addHospital(hospital)
        assertTrue(hospitalReturn is BaseResponse.SuccessResponse)
        assertEquals(hospital,hospitalReturn.data)
    }


    /**
     * Test za pronalazenje bolnice po id kada je prosledjeni parametar null
     */
    @Test
    fun getHospitalById_nullTest()=runBlocking {
        val hospital=hospitalRepository.getHospitalById(null)
        assertTrue(hospital is BaseResponse.ErrorResponse)
        assertEquals("Nije prosledjen ispravan argument za pretragu bolnice",hospital.message)
    }
    /**
     * Test za pronalazenje bolnice po id kada ne postoji bolnica sa tim id
     */
    @Test
    fun getHospitalById_NemaPodatakaTest()=runBlocking {
        val hospital=hospitalRepository.getHospitalById("9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9")
        assertTrue(hospital is BaseResponse.ErrorResponse)
        assertEquals("Bolnica sa ovim podacima ne postoji",hospital.message)
    }
    /**
     * Test za pronalazenje bolnice po id kada  postoji bolnica sa tim id
     */
    @Test
    fun getHospitalById_uspesnoPronadjena()=runBlocking {
        val hospitalAdded=service.addHospital(hospital)
        val hospitalFinded=hospitalRepository.getHospitalById(hospitalAdded?.getId())
        assertTrue(hospitalFinded is BaseResponse.SuccessResponse)
        assertEquals(hospitalAdded,hospitalFinded.data)
    }
    /**
     * Test za pronalazenje bolnice po name kada je prosledjeni parametar null
     */
    @Test
    fun getHospitalByName_nullTest()=runBlocking {
        val hospital=hospitalRepository.getHospitalByName(null)
        assertTrue(hospital is BaseResponse.ErrorResponse)
        assertEquals("Nije prosledjen ispravan argument za pretragu bolnice",hospital.message)
    }
    /**
     * Test za pronalazenje bolnice po name kada ne postoji bolnica sa tim imenom
     */
    @Test
    fun getHospitalByName_NemaPodatakaTest()=runBlocking {
        val hospital=hospitalRepository.getHospitalById("9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9")
        assertTrue(hospital is BaseResponse.ErrorResponse)
        assertEquals("Bolnica sa ovim podacima ne postoji",hospital.message)
    }
    /**
     * Test za pronalazenje bolnice po name kada  postoji bolnica sa tim imenom
     */
    @Test
    fun getHospitalByName_uspesnoPronadjena()=runBlocking {
        val hospitalAdded=service.addHospital(hospital)
        val hospitalFinded=hospitalRepository.getHospitalByName(hospitalAdded?.getName())
        assertTrue(hospitalFinded is BaseResponse.SuccessResponse)
        assertEquals(hospitalAdded,hospitalFinded.data)
    }
    /**
     * Test za pronalazenje bolnice po city kada je prosledjeni parametar null
     */
    @Test
    fun getHospitalsInCity_nullTest()=runBlocking {
        val hospital=hospitalRepository.getHospitalsInCity(null)
        assertTrue(hospital is ListResponse.ErrorResponse)
        assertEquals("Nije prosledjen ispravan argument za pretragu bolnice",hospital.message)
    }
    /**
     * Test za pronalazenje bolnice po city kada nema bolnica u tom gradu
     */
    @Test
    fun getHospitalsInCity_NemaPodatakaTest()=runBlocking {
        val hospital=hospitalRepository.getHospitalsInCity("9d2f5c36-ff62-4c0c-87cf-8a25c5d7b7a9")
        assertTrue(hospital is ListResponse.ErrorResponse)
        assertEquals("Bolnice u ovom gradu ne postoji",hospital.message)
    }
    /**
     * Test za pronalazenje bolnice po city kada ima bolnica u tom gradu
     */
    @Test
    fun getHospitalsInCity_uspesnoPronadjena()=runBlocking {
        val hospitalAdded=service.addHospital(hospital)
        val hospitalFinded=hospitalRepository.getHospitalsInCity(hospitalAdded?.getCity())
        assertTrue(hospitalFinded is ListResponse.SuccessResponse)
        assertEquals(1,hospitalFinded.data?.size)
    }

    /**
     * Test za pronalazenje svih bolnica kada je tabela jos uvek prazna
     */
    @Test
    fun getAllHospitals_testNemaBolnica()=runBlocking {
        val hospitals=hospitalRepository.getAllHospitals()
        assertTrue (hospitals is ListResponse.ErrorResponse)
        assertEquals("Bolnice jos ne postoje",hospitals.message)
    }

    /**
     * Test za pronalazenje svih bolnica kada su uspesno pronadjene
     */
    @Test
    fun getAllHospitals_testBolnicePronadjene()=runBlocking {
        service.addHospital(hospital)
        val hospitals=hospitalRepository.getAllHospitals()
        assertTrue (hospitals is ListResponse.SuccessResponse)
        assertEquals(hospital,hospitals.data?.last())
    }






}