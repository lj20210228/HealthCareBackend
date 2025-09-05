package com.example.service.doctor

import com.example.domain.Doctor
import com.example.service.doctor.DoctorServiceInterface
import kotlinx.serialization.json.Json
import java.io.File

/**
 * Klasa koja sluzi za implementaciju [DoctorServiceInterface] interfejsa
 * @property file Fajl koji se koristi za lokalno testiranje, za Json fajlove
 * @property doctorList Lokalna lista koja zajedno sa fajlom simulira tabelu u bazi
 * @author Lazar Janković
 * @see DoctorServiceInterface
 */
class DoctorServiceImplementation: DoctorServiceInterface {


    val file= File("doctors.json")
    private var doctorList=getAllDoctors()

    /**
     * Dodaje novog lekara
     * @see DoctorServiceInterface
     * @see com.example.domain.Doctor
     */
    override suspend fun addDoctor(doctor: Doctor?): Doctor {
        if (doctor==null)
            throw NullPointerException("Nisu ispravni prosledjeni podaci o lekaru")
        if (doctorList.contains(doctor))
            throw IllegalArgumentException("Lekar vec postoji")

        doctorList.add(doctor)
        val jsonString= Json { prettyPrint = true }.encodeToString(doctorList)
        file.writeText(jsonString)


        return doctor

    }

    /**
     * Pronalazi lekara po id
     * @see Doctor
     * @see DoctorServiceInterface
     */
    override suspend fun getDoctorForId(doctorId: String?): Doctor? {
        if(doctorId==null)
            throw NullPointerException("Id lekara ne sme biti null")
        if(doctorId.isEmpty())
            throw IllegalArgumentException("Id lekar ne sme biti prazan string")


        return doctorList.find { it.getId()==doctorId }

    }

    /**
     * Pronalazi sve lekare u jednoj bolnici
     * @see Doctor
     * @see DoctorServiceInterface
     */
    override suspend fun getAllDoctorsInHospital(hospitalId: String?): List<Doctor> {
        if (hospitalId==null)
            throw NullPointerException("Id bolnice ne sme biti null")
        if(hospitalId.isEmpty())
            throw IllegalArgumentException("Id bolnice ne sme biti prazan string")
        return doctorList
    }

    /**
     * Pronalazi sve lekare opste prakse u jednoj bolnici
     * @see DoctorServiceInterface
     * @see Doctor
     */
    override suspend fun getAllGeneralDoctorsInHospital(hospitalId: String?): List<Doctor> {
        if (hospitalId==null)
            throw NullPointerException("Id bolnice ne sme biti null")
        if(hospitalId.isEmpty())
            throw IllegalArgumentException("Id bolnice ne sme biti prazan string")
        return doctorList.filter{doctor -> doctor.getIsGeneral()}
    }

    /**
     *
     * Pronalazi sve lekare odredjene specijalizacije u jednoj bolnici
     * @see DoctorServiceInterface
     * @see Doctor
     *
     */
    override suspend fun getAllDoctorInHospitalForSpecialization(
        hospitalId: String?,
        specialization: String?
    ): List<Doctor> {
        if (hospitalId==null)
            throw NullPointerException("Id bolnice ne sme biti null")
        if(hospitalId.isEmpty())
            throw IllegalArgumentException("Id bolnice ne sme biti prazan string")
        if (specialization==null)
            throw NullPointerException("Specijalizacije lekara ne sme biti null")
        if(specialization.isEmpty())
            throw IllegalArgumentException("Specijalizacija lekara ne sme biti prazan string")
        return doctorList.filter{doctor -> doctor.getSpecialization()==specialization&&doctor.getHospitalId()==hospitalId}
    }

    /**
     * Funkcija koja ucitava listu svih lekara u JSON fajlu
     * @return [MutableList[Doctor]] Lista svih lekara
     */
     fun getAllDoctors(): MutableList<Doctor>{
        if(!file.exists()||file.readText().isBlank())return mutableListOf()
        val jsonString=file.readText()
        return Json.Default.decodeFromString<MutableList<Doctor>>(jsonString)
    }
}