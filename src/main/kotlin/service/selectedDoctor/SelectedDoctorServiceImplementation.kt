package com.example.service.selectedDoctor

import com.example.domain.Doctor
import com.example.domain.Patient
import com.example.domain.SelectedDoctor
import com.example.service.doctor.DoctorServiceInterface
import com.example.service.patient.PatientServiceInterface
import kotlinx.serialization.json.Json
import java.io.File

/**
 * Klasa koja implementira metode za rukovanje podacima o izabranim lekarima
 *
 * @author Lazar Janković
 *
 * @param doctorServiceInterface Ovde se nalaze metode za rukovanje podacima o lekaru
 * @param patientService Ovde se nalaze metode za rukovanje podacima o pacijentima
 * @property file Fajl koji simulira tabelu u bazi
 * @property listOfSelectedDoctors Parametar koji sluzi kao lista za komunikaciju sa json datotekama
 * @see DoctorServiceInterface
 * @see PatientServiceInterface
 *
 */
class SelectedDoctorServiceImplementation(val doctorServiceInterface: DoctorServiceInterface,val patientService: PatientServiceInterface): SelectedDoctorServiceInterface {
    val file= File("selectedDoctors.json")
    val listOfSelectedDoctors=getAllSelectedDoctors()

    /**
     * Dodavanje izabranog lekara pacijentu i povecavnje [Doctor.currentPatients]
     */
    override suspend fun addSelectedDoctorForPatient(selectedDoctor: SelectedDoctor?): SelectedDoctor {
        if (selectedDoctor==null)
            throw NullPointerException("Podaci o izabranom lekaru ne smeju biti null")
        if (listOfSelectedDoctors.contains(selectedDoctor)){
            throw IllegalArgumentException("Izabrani lekar za ovog pacijenta vec postoji.")
        }
        listOfSelectedDoctors.add(selectedDoctor)
        doctorServiceInterface.editCurrentPatients(selectedDoctor.getDoctorId())
        val json=Json { prettyPrint=true}.encodeToString(listOfSelectedDoctors)
        file.writeText(json)
        return selectedDoctor
    }

    /**
     * Vracanje liste lekara za jednog pacijenta, dohvatanjem jednog po jednog lekara preko [doctorServiceInterface]
     */
    override suspend fun getSelectedDoctorsForPatients(patientId: String?): List<Doctor> {
        if (patientId==null)
            throw NullPointerException("Id pacijenta za koga se traze izabrani lekari ne sme biti null")
        if(patientId.isEmpty()){
            throw IllegalArgumentException("Id pacijenta za koga se traze izabrani lekari ne moze biti prazan")
        }
        return listOfSelectedDoctors
            .filter { it.getPatientId()==patientId }
            .mapNotNull { selectedDoctor ->
                doctorServiceInterface.getDoctorForId(selectedDoctor.getDoctorId())
            }

    }

    /**
     * Vracanje liste pacijenata kojima je lekar izabrani pomocu metode iz [patientService]
     */
    override suspend fun getPatientsForSelectedDoctor(doctorId: String?): List<Patient> {
        if (doctorId==null)
            throw NullPointerException("Id lekara za koga se traze pacijenti ne sme biti null")
        if(doctorId.isEmpty()){
            throw IllegalArgumentException("Id lekara za koga se traze pacijenti ne moze biti prazan")
        }
        return listOfSelectedDoctors.filter { selectedDoctor ->
            selectedDoctor.getDoctorId()==doctorId
        }.mapNotNull { selectedDoctor ->
            patientService.getPatientById(selectedDoctor.getPatientId())
        }
    }

    /**
     * Dohvatanje json stringa i pretvaranje u [MutableList[SelectedDoctor]]
     */
    fun getAllSelectedDoctors(): MutableList<SelectedDoctor>{
        if (!file.exists()||file.readText().isEmpty())return mutableListOf()
        val jsonString=file.readText()
        return Json.decodeFromString<MutableList<SelectedDoctor>>(jsonString)
    }



}