package com.example.routes

import com.example.repository.auth.AuthRepository
import com.example.request.LoginRequest
import com.example.request.RegisterRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post

fun Routing.authRoutes(service: AuthRepository){
    get("/cao"){
        call.respond("Cao")
    }
    post("/register") {
        try {

            val params = call.receive<RegisterRequest>()
            println(params)
            val response = service.registerUser(params)
            call.respond(status = HttpStatusCode.fromValue(response.statusCode), response)
        } catch (e: Exception) {
            e.printStackTrace()
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
        }
    }
    post("/login") {
        try {

            val params = call.receive<LoginRequest>()
            println(params)
            val response = service.loginUser(params)
            call.respond(status = HttpStatusCode.fromValue(response.statusCode), response)
        } catch (e: Exception) {
            e.printStackTrace()
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
        }
    }
}