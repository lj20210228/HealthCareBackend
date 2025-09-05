package com.example.service

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