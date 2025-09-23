package com.example.service.patient

import com.example.domain.Patient
import kotlinx.serialization.json.Json
import java.io.File

/**
 * Klasa koja implementira metode interfejsa [PatientServiceInterface]
 * @author Lazar Janković
 * @see PatientServiceInterface
 * @see Patient
 * @property listOfPatients Lista pacijenata koju cemo pretrazivati i u nju dodavati pacijente
 * @property file Json fajl koji simulira tabelu u bazi
 *
 */
class PatientServiceImplementation: PatientServiceInterface {
    val file= File("patients.json")
    val listOfPatients=getAllPatients()


    /**
     * Funkcija za dodavanje pacijenta
     */
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

    /**
     * Funckija za pronalazenje pacijenta po id
     */
    override suspend fun getPatientById(patientId: String?): Patient? {
        if (patientId==null)
            throw NullPointerException("Prosledjeni patientId jendak je null")
        if(patientId.isEmpty()){
            throw IllegalArgumentException("Id pacijenta ne moze biti null")

        }
        return listOfPatients.find { patient -> patient.getId()==patientId }

    }

    /**
     * Funckija za pronalazenje pacijenta po jmbg
     */
    override suspend fun getPatientByJmbg(jmbg: String?): Patient? {
        if (jmbg==null)
            throw NullPointerException("Prosledjeni jmbg jendak je null")
        if(jmbg.isEmpty()){
            throw IllegalArgumentException("Jmbg ne moze biti null")

        }
        if(!jmbg.all { it.isDigit() }){
            throw IllegalArgumentException("Jmbg mora sadrzati samo brojeve")

        }
        if(jmbg.length!=13){
            throw IllegalArgumentException("Duzina jmbg-a mora biti 13 cifara")

        }
        return listOfPatients.find { patient -> patient.getJmbg()==jmbg }
    }

    /**
     * Funkcija za pronalanje pacijenata iz jedne bolnice
     */
    override suspend fun getAllPatientInHospital(hospitalId: String?): List<Patient> {
        if (hospitalId==null)
            throw NullPointerException("Prosledjeni hospital jendak je null")
        if(hospitalId.isEmpty()){
            throw IllegalArgumentException("Id bolnice ne moze biti null")

        }
        return listOfPatients.filter { it.getHospitalId()==hospitalId }
    }

    /**
     * Funkcija za ucitavanje svih pacijenata iz json fajla
     */
    fun getAllPatients(): MutableList<Patient>{
        if (!file.exists()||file.readText().isBlank())return mutableListOf()
        val jsonString=file.readText()
        return Json.Default.decodeFromString<MutableList<Patient>>(jsonString)
    }


}