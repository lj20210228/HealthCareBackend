package com.example.service

import com.example.service.patient.PatientServiceImplementation
import com.example.service.patient.PatientServiceInterface

class PatientServiceImplementationTest: PatientServiceInterfaceTest() {
    override fun getInstance(): PatientServiceInterface {
        return PatientServiceImplementation()
    }
}