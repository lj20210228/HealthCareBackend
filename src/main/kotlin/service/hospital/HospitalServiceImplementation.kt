package com.example.service.hospital

import com.example.domain.Hospital
import kotlinx.serialization.json.Json
import java.io.File

/**
 * Klasa koja implementira metode [HospitalServiceInterface]
 * @author Lazar Janković
 * @see HospitalServiceInterface
 * @property file Json fajl koji simulira kolonu u bazi
 * @property list Lista bolnica koja se ucitava iz i u json fajl
 *
 */
class HospitalServiceImplementation: HospitalServiceInterface {

    val file= File("hospitals.json")
    val list=getHospitals()

    /**
     * Dodavanje nove bolnice
     */
    override suspend fun addHospital(hospital: Hospital?): Hospital {
        if (hospital==null)
            throw NullPointerException("Bolnica ne moze biti null")
        if (list.contains(hospital))
            throw IllegalArgumentException("Bolnica vec postoji")
        list.add(hospital)
        val jsonString=Json { prettyPrint=true }.encodeToString(list)
        file.writeText(jsonString)
        return hospital
    }

    /**
     * Trazenje bolnice po id
     */
    override suspend fun getHospitalById(hospitalId: String?): Hospital? {
        if (hospitalId==null)
            throw NullPointerException("Id bolnice ne moze biti null")
        if (hospitalId.isEmpty())
            throw NullPointerException("Id bolnice ne moze biti prazan string")
        return list.find { it.getId()==hospitalId }

    }
    /**
     * Trazenje bolnice po imenu
     */
    override suspend fun getHospitalByName(name: String?): Hospital? {
        if (name==null)
            throw NullPointerException("Ime bolnice ne moze biti null")
        if (name.isEmpty())
            throw NullPointerException("Ime bolnice ne moze biti prazan string")
        return list.find { it.getName()==name }
    }
    /**
     * Trazenje svih bolnica u gradu
     */
    override suspend fun getHospitalsInCity(city: String?): List<Hospital> {
        if (city==null)
            throw NullPointerException("Ime grada ne moze biti null")
        if (city.isEmpty())
            throw NullPointerException("Ime grada ne moze biti prazan string")
        return list.filter { it.getCity()==city }
    }

    /**
     * Pronalazak svih bolnica
     */
    override suspend fun getAllHospitals(): List<Hospital> {
        return getHospitals()
    }

    /**
     * Metoda za dohvatanje svih podataka iz json fajla
     */
    fun getHospitals(): MutableList<Hospital>{
        if (!file.exists()||file.readText().isBlank())return mutableListOf()
        val jsonString=file.readText()
        return Json.Default.decodeFromString<MutableList<Hospital>>(jsonString)
    }
}