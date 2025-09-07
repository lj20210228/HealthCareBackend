package com.example.service.hospital

import com.example.domain.Hospital
import kotlinx.serialization.json.Json
import java.io.File

class HospitalServiceImplementation: HospitalServiceInterface {

    val file= File("hospitals.json")
    val list=getHospitals()

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

    override suspend fun getHospitalById(hospitalId: String?): Hospital? {
        if (hospitalId==null)
            throw NullPointerException("Id bolnice ne moze biti null")
        if (hospitalId.isEmpty())
            throw NullPointerException("Id bolnice ne moze biti prazan string")
        return list.find { it.getId()==hospitalId }

    }

    override suspend fun getHospitalByName(name: String?): Hospital? {
        if (name==null)
            throw NullPointerException("Ime bolnice ne moze biti null")
        if (name.isEmpty())
            throw NullPointerException("Ime bolnice ne moze biti prazan string")
        return list.find { it.getName()==name }
    }

    override suspend fun getHospitalsInCity(city: String?): List<Hospital> {
        if (city==null)
            throw NullPointerException("Ime grada ne moze biti null")
        if (city.isEmpty())
            throw NullPointerException("Ime grada ne moze biti prazan string")
        return list.filter { it.getCity()==city }
    }

    override suspend fun getAllHospitals(): List<Hospital> {
        return getHospitals()
    }

    fun getHospitals(): MutableList<Hospital>{
        if (!file.exists()||file.readText().isBlank())return mutableListOf()
        val jsonString=file.readText()
        return Json.Default.decodeFromString<MutableList<Hospital>>(jsonString)
    }
}