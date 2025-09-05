package com.example.service

import com.example.service.doctor.DoctorServiceImplementation
import com.example.service.patient.PatientServiceImplementation
import com.example.service.patient.PatientServiceInterface
/**
 * Klasa koja nasledjuje [PatientServiceInterfaceTest]
 * @author Lazar Janković
 * @see PatientServiceImplementation
 * @see PatientServiceInterface
 *
 */
class PatientServiceImplementationTest: PatientServiceInterfaceTest() {
    /**
     * Metoda koja vraca instancu klase koja nasledjuje [PatientServiceInterface]
     * @return [PatientServiceImplementation]
     */
    override fun getInstance(): PatientServiceInterface {
        return PatientServiceImplementation()
    }
}