package com.example.routes

import com.example.domain.Patient
import com.example.repository.patient.PatientRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put

fun Route.patientRoutes(repository: PatientRepository){
    post("/patients/add") {
        val params=call.receive<Patient>()
        val patient=repository.addPatient(params)
        call.respond(status = HttpStatusCode.fromValue(patient.statusCode),patient)
    }
    get("/patients/{id}") {
        val id=call.parameters["id"]
        val patients=repository.getPatientById(id)
        call.respond(status = HttpStatusCode.fromValue(patients.statusCode),patients)


    }
    get("/patient/userId/{userId}") {

        val userId=call.parameters["userId"]
        val patient=repository.getPatientByUserId(userId)
        call.respond(status = HttpStatusCode.fromValue(patient.statusCode),patient)

    }
    get("/patients/hospitals/{hospitalId}") {
        val id=call.parameters["hospitalId"]
        val patients=repository.getAllPatientInHospital(id)
        call.respond(status = HttpStatusCode.fromValue(patients.statusCode),patients)
    }
    get("/patients/jmbg/{jmbg}") {
        val jmbg=call.parameters["jmbg"]
        val patient=repository.getPatientByJmbg(jmbg)
        call.respond(status = HttpStatusCode.fromValue(patient.statusCode),patient)
    }
    put ("/patients/edit"){
        val params=call.receive<Patient>()
        val patient=repository.editPatient(params)
        call.respond(status = HttpStatusCode.fromValue(patient.statusCode),patient)
    }
    delete("/patients/{id}") {
        val id=call.parameters["id"]
        val patient=repository.deletePatient(id)
        call.respond(status = HttpStatusCode.fromValue(patient.statusCode),patient)

    }
}
