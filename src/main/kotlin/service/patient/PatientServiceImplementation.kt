package com.example.service.patient

import com.example.domain.Patient
import kotlinx.serialization.json.Json
import java.io.File

class PatientServiceImplementation: PatientServiceInterface {
    val file= File("patients.json")
    val listOfPatients=getAllPatients()


    override suspend fun addPatient(patient: Patient?): Patient {
        if (patient==null)
            throw NullPointerException("Prosledjeni podaci o pacijentu su jednaki null")
        if (listOfPatients.contains(patient)){
            throw IllegalArgumentException("Pacijent vec postoji")
        }
        listOfPatients.add(patient)
        val jsonString=Json { prettyPrint=true }.encodeToString(listOfPatients)
        file.writeText(jsonString)
        return patient


    }

    override suspend fun getPatientById(patientId: String?): Patient? {
        if (patientId==null)
            throw NullPointerException("Prosledjeni patientId jendak je null")
        if(patientId.isEmpty()){
            throw IllegalArgumentException("Id pacijenta ne moze biti null")

        }
        return listOfPatients.find { patient -> patient.getId()==patientId }

    }

    override suspend fun getAllPatientInHospital(hospitalId: String?): List<Patient> {
        if (hospitalId==null)
            throw NullPointerException("Prosledjeni hospital jendak je null")
        if(hospitalId.isEmpty()){
            throw IllegalArgumentException("Id bolnice ne moze biti null")

        }
        return listOfPatients.filter { it.getHospitalId()==hospitalId }
    }
    fun getAllPatients(): MutableList<Patient>{
        if (!file.exists()||file.readText().isBlank())return mutableListOf()
        val jsonString=file.readText()
        return Json.Default.decodeFromString<MutableList<Patient>>(jsonString)
    }


}