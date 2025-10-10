package com.example.routes

import com.example.domain.WorkTime
import com.example.repository.workTime.WorkTimeRepository
import io.ktor.client.plugins.HttpSend
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put

fun Route.workTimeRoutes(repository: WorkTimeRepository){
    get("/worktime/doctor/{id}") {
        val param=call.parameters["id"]
        val result=repository.getWorkTimeForDoctor(param)
        call.respond(status = HttpStatusCode.fromValue(result.statusCode),result)
    }
    post ("/worktime/add") {
        val param=call.receive<WorkTime>()
        val result=repository.addWorkTime(param)
        call.respond(status = HttpStatusCode.fromValue(result.statusCode),result)
    }
    put  ("/worktime/update") {
        val param=call.receive<WorkTime>()
        val result=repository.updateWorkingTime(param)
        call.respond(status = HttpStatusCode.fromValue(result.statusCode),result)
    }
    delete("/worktime/delete/{id}") {
        val param=call.parameters["id"]
        val result=repository.deleteWorkingTime(param)
        call.respond(status = HttpStatusCode.fromValue(result.statusCode),result)
    }
}