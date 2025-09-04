package com.example.service

import com.example.domain.Doctor

class DoctorServiceImplementation: DoctorServiceInterface {
    override suspend fun addDoctor(doctor: Doctor?): Doctor {
        TODO("Not yet implemented")
    }

    override suspend fun getDoctorForId(doctorId: String?): Doctor? {
        TODO("Not yet implemented")
    }

    override suspend fun getAllDoctorsInHospital(hospitalId: String?): List<Doctor> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllGeneralDoctorsInHospital(hospitalId: String?): List<Doctor> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllDoctorInHospitalForSpecialization(
        hospitalId: String?,
        specialization: String?
    ): List<Doctor> {
        TODO("Not yet implemented")
    }
}