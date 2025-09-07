package com.example.service

import com.example.domain.Hospital
import com.example.service.hospital.HospitalServiceImplementation
import com.example.service.hospital.HospitalServiceInterface
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import java.io.File

class HospitalServiceInterfaceTest {
     var hospitalService: HospitalServiceInterface?=null
    val file= File("hospitals.json")
    @BeforeEach
    fun setUp(){
        hospitalService= HospitalServiceImplementation()
        file.writeText("")
    }
    @AfterEach
    fun tearDown(){
        file.writeText("")

        hospitalService=null
    }
}