package com.example.routes

import com.example.domain.SelectedDoctor
import com.example.repository.selectedDoctor.SelectedDoctorRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post

fun Route.selectedDoctorRoutes(repository: SelectedDoctorRepository){
    get("/selectedDoctors/patient/{patientId}") {
        val patientId=call.parameters["patiendId"]
        val doctors=repository.getSelectedDoctorsForPatients(patientId)
        call.respond(status = HttpStatusCode.fromValue(doctors.statusCode),doctors)
    }
    get("/selectedDoctors/doctors/{doctorId}") {
        val doctorsId=call.parameters["doctorId"]
        val patients=repository.getSelectedDoctorsForPatients(doctorsId)
        call.respond(status = HttpStatusCode.fromValue(patients.statusCode),patients)
    }
    post("/selectedDoctors") {
        val params=call.receive<SelectedDoctor>()
        val selDoctor=repository.addSelectedDoctorForPatient(params)
        call.respond(status = HttpStatusCode.fromValue(selDoctor.statusCode),selDoctor)


    }

}