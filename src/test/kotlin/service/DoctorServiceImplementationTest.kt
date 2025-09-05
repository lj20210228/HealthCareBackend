package com.example.service

import com.example.service.doctor.DoctorServiceImplementation

/**
 * Klasa koja nasledjuje [DoctorServiceInterfaceTest]
 * @author Lazar Janković
 * @see DoctorServiceImplementation
 * @see DoctorServiceInterface
 *
 */
class DoctorServiceImplementationTest: DoctorServiceInterfaceTest() {
    /**
     * Metoda koja vraca instancu klase koja nasledjuje [DoctorServiceInterface]
     * @return [DoctorServiceImplementation]
     */
    override fun getInstance(): DoctorServiceInterface {
        return DoctorServiceImplementation()
    }

}