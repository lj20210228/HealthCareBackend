package com.example.service.doctor

import com.example.domain.Doctor
import com.example.service.DoctorServiceInterface
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
    private var doctorList=getAllDoctors(file)

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
            throw IllegalArgumentException("Id lekara ne sme biti prazan string")


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
     * Pronalazi sve lekare opste prakse u jednoj bolnici koji imaju mesta da budu izabrani
     * @see DoctorServiceInterface
     * @see Doctor
     */
    override suspend fun getAllGeneralDoctorsInHospitalWithoutMaxPatients(hospitalId: String?): List<Doctor> {
        if (hospitalId==null)
            throw NullPointerException("Id bolnice ne sme biti null")
        if(hospitalId.isEmpty())
            throw IllegalArgumentException("Id bolnice ne sme biti prazan string")
        return doctorList.filter{doctor -> doctor.getIsGeneral()&&doctor.getCurrentPatients()<doctor.getMaxPatients()}
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
     *
     * Pronalazi sve lekare odredjene specijalizacije u jednoj bolnici, koji imaju mesta da budu izabrani
     * @see DoctorServiceInterface
     * @see Doctor
     *
     */
    override suspend fun getAllDoctorsInHospitalWithoutMaxPatientsForSpecialization(hospitalId: String?,specialization: String?): List<Doctor> {
        if (hospitalId==null)
            throw NullPointerException("Id bolnice ne sme biti null")
        if(hospitalId.isEmpty())
            throw IllegalArgumentException("Id bolnice ne sme biti prazan string")
        if (specialization==null)
            throw NullPointerException("Specijalizacije lekara ne sme biti null")
        if(specialization.isEmpty())
            throw IllegalArgumentException("Specijalizacija lekara ne sme biti prazan string")
        return doctorList.filter{doctor -> doctor.getSpecialization()==specialization&&doctor.getHospitalId()==hospitalId&&doctor.getCurrentPatients()<doctor.getMaxPatients()}
    }

    /**
     * Povecava broj trenutnih pacijenata za 1
     * @see Doctor
     */
    override suspend fun editCurrentPatients(doctorId: String?): Boolean {
        if (doctorId==null)
            throw NullPointerException("Id lekara ne sme biti null")
        if(doctorId.isEmpty())
            throw IllegalArgumentException("Id lekara ne sme biti prazan string")
        val index = doctorList.indexOfFirst { it.getId() == doctorId }
        if (index == -1) return false

        val doctor = doctorList[index]

        if (doctor.getCurrentPatients() >= doctor.getMaxPatients()) return false

        // Napravi novu instancu sa povećanim brojem pacijenata
        val updatedDoctor = doctor.copy(currentPatients = doctor.getCurrentPatients() + 1)

        // Zameni staru instancu u listi
        doctorList[index] = updatedDoctor

        // Upisi u fajl
        val json = Json { prettyPrint = true }.encodeToString(doctorList)
        file.writeText(json)

        return true


    }
    /**
     * Funkcija koja ucitava listu svih lekara u JSON fajlu
     * @return [MutableList[Doctor]] Lista svih lekara
     */
     public fun getAllDoctors(file: File): MutableList<Doctor>{
        if(!file.exists()||file.readText().isBlank())return mutableListOf()
        val jsonString=file.readText()
        return Json.Default.decodeFromString<MutableList<Doctor>>(jsonString)
    }
}