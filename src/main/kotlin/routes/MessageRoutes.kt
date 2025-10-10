package com.example.routes

import com.example.domain.Message
import com.example.repository.message.MessageRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post

fun Route.messageRoutes(repository: MessageRepository){
    get("/messages/{id}") {
        val id=call.parameters["id"]
        val result=repository.getMessage(id)
        call.respond(status = HttpStatusCode.fromValue(result.statusCode),result)

    }
    get("/messages/recepient/{id}") {
        val id=call.parameters["id"]
        val result=repository.getMessagesForRecepient(id)
        call.respond(status = HttpStatusCode.fromValue(result.statusCode),result)

    }
    get("/messages/sender/{id}") {
        val id=call.parameters["id"]
        val result=repository.getMessagesForSender(id)
        call.respond(status = HttpStatusCode.fromValue(result.statusCode),result)

    }
    post("/messages/add") {
        val params=call.receive<Message>()
        val result=repository.addMessage(message = params)
        call.respond(status = HttpStatusCode.fromValue(result.statusCode),result)



    }

}