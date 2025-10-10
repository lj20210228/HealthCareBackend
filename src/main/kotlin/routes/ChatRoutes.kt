package com.example.routes

import com.example.domain.Chat
import com.example.repository.chat.ChatRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post

fun Route.chatRoutes(repository: ChatRepository){
    get("/chat/{id}") {
        val id=call.parameters["id"]
        val response=repository.getChat(id)
        call.respond(status = HttpStatusCode.fromValue(response.statusCode),response)
    }
    get("/chat/patient/{id}") {
        val id=call.parameters["id"]
        val response=repository.getChatsForPatient(id)
        call.respond(status = HttpStatusCode.fromValue(response.statusCode),response)
    }
    get("/chat/doctor/{id}") {
        val id=call.parameters["id"]
        val response=repository.getChatsForDoctor(id)
        call.respond(status = HttpStatusCode.fromValue(response.statusCode),response)
    }
    post {
        val params=call.receive<Chat>()
        val response=repository.addChat(params)
        call.respond(status = HttpStatusCode.fromValue(response.statusCode),response)

    }
}