package com.example.service.selectedDoctor

import com.example.domain.Doctor
import com.example.domain.Patient
import com.example.domain.SelectedDoctor


interface SelectedDoctorServiceInterface {
    suspend fun addSelectedDoctorForPatient(selectedDoctor: SelectedDoctor): SelectedDoctor
    suspend fun getSelectedDoctorsForPatients(patientId: String?): List<Doctor>
    suspend fun getPatientsForSelectedDoctor(doctorId: String?): List<Patient>

}