package com.example.routes

import com.example.domain.Termin
import com.example.repository.termin.TerminRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import java.time.LocalDate
import java.util.UUID

fun Route.terminRoutes(repository: TerminRepository){
    post("/termin/add") {
        val params=call.receive<Termin>()
        val result=repository.addTermin(params)
        call.respond(status = HttpStatusCode.fromValue(result.statusCode),result)

    }
    put ("/termin/edit") {
        val params=call.receive<Termin>()
        val result=repository.editTermin(params)
        call.respond(status = HttpStatusCode.fromValue(result.statusCode),result)

    }
    delete("/termin/delete/{id}") {
        val param=call.parameters["id"]
        val result=repository.deleteTermin(param)
        call.respond(status = HttpStatusCode.fromValue(result.statusCode),result)

    }
    get("/termin/{id}") {
        val param=call.parameters["id"]
        val result=repository.getTerminForId(param)
        call.respond(status = HttpStatusCode.fromValue(result.statusCode),result)


    }
    get("/termin/doctor/{id}") {
        val param=call.parameters["id"]
        val result=repository.getTerminsForDoctor(param)
        call.respond(status = HttpStatusCode.fromValue(result.statusCode),result)


    }
    get("/termin/patient/{id}") {
        val param=call.parameters["id"]
        val result=repository.getTerminsForPatient(param)
        call.respond(status = HttpStatusCode.fromValue(result.statusCode),result)


    }
    get("/termin/doctor/{doctorId}/termins") {
        val doctorId = UUID.fromString(call.parameters["doctorId"]) ?: throw BadRequestException("Doctor ID is missing or invalid.")

        val dateString = call.request.queryParameters["date"]

        val date = try {
            LocalDate.parse(dateString)
        } catch (e: Exception) {
            throw BadRequestException("Date parameter is missing or in an incorrect format (use YYYY-MM-DD).")
        }

        val result = repository.getTerminsForDoctorForDate(
            doctorId = doctorId.toString(),
            date = date
        )

        call.respond(HttpStatusCode.OK, result)
    }
    get("/termin/patient/{patientId}/termins") {
        val doctorId = UUID.fromString(call.parameters["patientId"]) ?: throw BadRequestException("Doctor ID is missing or invalid.")

        val dateString = call.request.queryParameters["date"]

        val date = try {
            LocalDate.parse(dateString)
        } catch (e: Exception) {
            throw BadRequestException("Date parameter is missing or in an incorrect format (use YYYY-MM-DD).")
        }

        val result = repository.getTerminsForPatientForDate(
            patientId = doctorId.toString(),
            date = date
        )

        call.respond(HttpStatusCode.OK, result)
    }
}