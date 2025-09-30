package com.example.routes

import com.example.domain.User
import com.example.repository.user.UserRepository
import io.ktor.http.HttpStatusCode
import io.ktor.http.hostIsIp
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post

fun Route.userRoutes( repository: UserRepository){
    post ("/users/add"){
        val params=call.receive<User>()
        val user=repository.addUser(params)
        call.respond(status = HttpStatusCode.fromValue(user.statusCode),user)
    }
    get("/users/{id}"){
        val id=call.parameters["id"]
        val user=repository.getUserById(id)
        call.respond(status = HttpStatusCode.fromValue(user.statusCode),user)

    }
}