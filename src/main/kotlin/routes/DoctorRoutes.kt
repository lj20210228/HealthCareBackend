package com.example.routes

import com.example.domain.Doctor
import com.example.repository.doctor.DoctorRepository
import com.example.request.DoctorRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post

fun Route.doctorRoutes( doctorRepository: DoctorRepository){
    post("/doctors/add") {
        val params=call.receive<Doctor>()
        val doctor=doctorRepository.addDoctor(params)
        call.respond(status = HttpStatusCode.fromValue(doctor.statusCode),doctor)

    }
    get("/doctor/{id}") {
        val id=call.parameters["id"]
        val doctor=doctorRepository.getDoctorForId(id)
        call.respond(status = HttpStatusCode.fromValue(doctor.statusCode),doctor)

    }
    get("/doctor/hospital/{hospitalId}") {
        val id=call.parameters["hospitalId"]
        val doctors=doctorRepository.getAllDoctorsInHospital(id)
        call.respond(status = HttpStatusCode.fromValue(doctors.statusCode),doctors)

    }
    get("/doctor/general/{hospitalId}") {
        val id=call.parameters["hospitalId"]
        val doctors=doctorRepository.getAllGeneralDoctorsInHospital(id)
        call.respond(status = HttpStatusCode.fromValue(doctors.statusCode),doctors)
    }
    get("/doctor/general/free/{hospitalId}") {
        val id=call.parameters["hospitalId"]
        val doctors=doctorRepository.getAllGeneralDoctorsInHospitalWithoutMaxPatients(id)
        call.respond(status = HttpStatusCode.fromValue(doctors.statusCode),doctors)
    }
    get("/doctor/{specialization}/{hospitalId}") {
        val id=call.parameters["hospitalId"]
        val specialization=call.parameters["specialization"]
        val doctors=doctorRepository.getAllDoctorInHospitalForSpecialization(id,specialization)
        call.respond(status = HttpStatusCode.fromValue(doctors.statusCode),doctors)
    }
    get("/doctor/free/{specialization}/{hospitalId}") {
        val id=call.parameters["hospitalId"]
        val specialization=call.parameters["specialization"]
        val doctors=doctorRepository.getAllDoctorsInHospitalWithoutMaxPatientsForSpecialization(id,specialization)
        call.respond(status = HttpStatusCode.fromValue(doctors.statusCode),doctors)
    }
}