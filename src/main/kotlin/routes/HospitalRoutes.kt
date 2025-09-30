package com.example.routes

import com.example.domain.Hospital
import com.example.repository.hospital.HospitalRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post

fun Route.hospitalRoutes(repository: HospitalRepository){
    post("/hospitals/add") {
        val params=call.receive<Hospital>()
        val hospital=repository.addHospital(params)
        call.respond(status = HttpStatusCode.fromValue(hospital.statusCode),hospital)
    }
    get("/hospitals"){
        val hospitals=repository.getAllHospitals()
        call.respond(status = HttpStatusCode.fromValue(hospitals.statusCode),hospitals)

    }
    get("/hospitals/{id}"){
        val param=call.parameters["id"]
        val hospital=repository.getHospitalById(param)
        call.respond(status = HttpStatusCode.fromValue(hospital.statusCode),hospital)

    }
    get("/hospitals/name/{name}"){
        val param=call.parameters["name"]
        val hospital=repository.getHospitalByName(param)
        call.respond(status = HttpStatusCode.fromValue(hospital.statusCode),hospital)

    }
    get("/hospitals/city/{city}"){
        val param=call.parameters["city"]
        val hospitals=repository.getHospitalsInCity(param)
        call.respond(status = HttpStatusCode.fromValue(hospitals.statusCode),hospitals)

    }
}