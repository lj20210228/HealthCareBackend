package com.example.service

class DoctorServiceImplementationTest: DoctorServiceInterfaceTest() {
    override fun getInstance(): DoctorServiceInterface {
        return DoctorServiceImplementation()
    }

}